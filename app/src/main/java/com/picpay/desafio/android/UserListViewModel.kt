package com.picpay.desafio.android

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.model.User
import kotlinx.coroutines.launch

class UserListViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList = _userList

    fun getUserList() {
        viewModelScope.launch {
            val response = usersRepository.getUserList()
            if (response.isSuccessful) {
                _userList.postValue(response.body())
            } else {

            }
        }
    }
}