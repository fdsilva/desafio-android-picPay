package com.picpay.desafio.android.data.service

import com.picpay.desafio.android.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PicPayService {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Response<List<User>>
}