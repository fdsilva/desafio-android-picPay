package com.picpay.desafio.android.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.picpay.desafio.android.data.service.PicPayService
import com.picpay.desafio.android.data.db.UserDataBase
import com.picpay.desafio.android.data.sources.UserRemoteMediator

private const val PAGE_SIZE = 10

class UsersRepository(
    private val userDataBase: UserDataBase,
    private val picPayService: PicPayService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getUserList() = Pager (
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
            enablePlaceholders = false
        ),
        remoteMediator = UserRemoteMediator (
            picPayService,
            userDataBase
        ),
        pagingSourceFactory = { userDataBase.usersDao().getAllUsers() }
    ).flow
}