package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.PicPayService

class UsersRepository(
    private val picPayService: PicPayService
) {
    suspend fun getUserList() = picPayService.getUsers()
}