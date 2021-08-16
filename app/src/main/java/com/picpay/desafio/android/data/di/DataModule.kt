package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.db.UserDataBase
import com.picpay.desafio.android.data.repository.UsersRepository
import org.koin.dsl.module

val dataModule = module {
    factory {
        UsersRepository(
            picPayService = get(),
            userDataBase = get()
        )
    }

    single {
        UserDataBase.getInstance(get())
    }
}