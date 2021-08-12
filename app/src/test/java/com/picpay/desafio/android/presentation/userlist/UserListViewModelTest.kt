package com.picpay.desafio.android.presentation.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.data.UsersRepository
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.utils.RuleCoroutinesTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {
    @get:Rule
    val coroutineRule = RuleCoroutinesTest()
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: UserListViewModel

    @Mock
    private lateinit var repository: UsersRepository

    @Mock
    private lateinit var userListObserver: Observer<List<User>>

    @Before
    fun setUp() {
        viewModel = UserListViewModel(repository)
    }

}