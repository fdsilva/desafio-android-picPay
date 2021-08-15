package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.db.UserDataBase
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