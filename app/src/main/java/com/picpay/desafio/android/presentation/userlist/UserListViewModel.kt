package com.picpay.desafio.android.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.picpay.desafio.android.data.repository.UsersRepository


class UserListViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    fun getUserList() = usersRepository.getUserList().cachedIn(viewModelScope)
}