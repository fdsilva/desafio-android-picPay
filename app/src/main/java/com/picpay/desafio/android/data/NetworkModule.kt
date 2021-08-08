package com.picpay.desafio.android

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.PicPayService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//TODO remove to const file or to gradle
private const val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

val networkModule = module {
    single { provideRetrofit() }
    factory { providePicPayService(retrofit = get()) }
}

fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(url)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

private val okHttp: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .build()
}

fun providePicPayService(retrofit: Retrofit): PicPayService = retrofit.create(PicPayService::class.java)

