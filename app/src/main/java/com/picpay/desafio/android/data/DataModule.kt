package com.picpay.desafio.android.data

import org.koin.dsl.module

val dataModule = module {
    factory {
        UsersRepository(picPayService = get())
    }
}