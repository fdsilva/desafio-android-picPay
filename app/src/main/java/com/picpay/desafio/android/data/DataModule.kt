package com.picpay.desafio.android.data

import com.picpay.desafio.android.UsersRepository
import org.koin.dsl.module

val dataModule = module {
    factory {
        UsersRepository(picPayService = get())
    }
}