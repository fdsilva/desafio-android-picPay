package com.picpay.desafio.android.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.picpay.desafio.android.data.PicPayService

class UsersRepository(
    private val picPayService: PicPayService
) {
    fun getUserList() = Pager(
        pagingSourceFactory = {UserListPagingDataSource(
            picPayService = picPayService
        )}, config = PagingConfig(
            pageSize = 5
        )
    ).flow
}