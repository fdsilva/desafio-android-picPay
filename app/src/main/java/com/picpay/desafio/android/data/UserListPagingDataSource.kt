package com.picpay.desafio.android.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.picpay.desafio.android.model.User

private const val INITIAL_PAGE = 1

class UserListPagingDataSource(
    private val picPayService: PicPayService
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        Log.d("DEBUG-TAG", "<<<<< CU >>>>>")
        return state.anchorPosition?.let {
            state.closestPageToPosition(anchorPosition = it)?.prevKey?.plus(INITIAL_PAGE)
                ?: state.closestPageToPosition(anchorPosition = it)?.nextKey?.minus(INITIAL_PAGE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            Log.d("DEBUG-TAG", "- PAGE ${page} >>>>>")
            //Log.d("DEBUG-TAG", "<<<<< ${params.loadSize}>>>>>")
            val response = picPayService.getUsers(page = page)

            response.body()?.let {
                LoadResult.Page(
                    data = it,
                    prevKey = if(page > 1) page -1 else null,
                    nextKey = if (page < it.size) page + 1 else null
                )
            }!!
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}