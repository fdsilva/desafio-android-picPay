package com.picpay.desafio.android.presentation.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.picpay.desafio.android.data.UsersRepository
import com.picpay.desafio.android.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserListViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _userList = MutableLiveData<PagingData<User>>()
    val userList = _userList
    fun getUserList() = usersRepository.getUserList().cachedIn(viewModelScope)

}