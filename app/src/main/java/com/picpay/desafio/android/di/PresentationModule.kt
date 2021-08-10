package com.picpay.desafio.android.di

import com.picpay.desafio.android.presentation.userlist.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        UserListViewModel(usersRepository = get())
    }
}