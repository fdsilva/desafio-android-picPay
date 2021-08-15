package com.picpay.desafio.android.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.picpay.desafio.android.data.db.UserDataBase
import com.picpay.desafio.android.model.RemoteKeys
import com.picpay.desafio.android.model.User
import retrofit2.HttpException
import java.io.IOException

private const val INITIAL_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val service: PicPayService,
    private val userDataBase: UserDataBase
) : RemoteMediator<Int, User>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state = state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val response = service.getUsers(page = page)
            val users = response.body()
            val endOfPaginationReached = users?.isEmpty()

            userDataBase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    userDataBase.remoteKeysDao().clearRemoteKeys()
                    userDataBase.usersDao().clearUsers()
                }
                val prevKey = if(page == INITIAL_PAGE) null else page -1
                val nextKey = if(endOfPaginationReached!!) null else page+1
                val keys = users.map {
                    RemoteKeys(userId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                userDataBase.remoteKeysDao().insertAll(keys)
                userDataBase.usersDao().insertAll(users)
            }
            return endOfPaginationReached?.let { MediatorResult.Success(endOfPaginationReached = it) }!!
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                userDataBase.remoteKeysDao().remoteKeysRepoId(user.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                userDataBase.remoteKeysDao().remoteKeysRepoId(user.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, User>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                userDataBase.remoteKeysDao().remoteKeysRepoId(userId)
            }
        }
    }

}