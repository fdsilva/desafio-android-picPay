package com.picpay.desafio.android.data.sources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.picpay.desafio.android.data.service.PicPayService
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.utils.RuleCoroutinesTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.lang.NullPointerException
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class UserRemoteMediatorTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTest = RuleCoroutinesTest()

    @Mock lateinit var api: PicPayService

    lateinit var pagingSource: UserListPagingDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        pagingSource = UserListPagingDataSource(api)
    }

    @Test
    fun `load paging with http failure`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(api.getUsers(any(), any())).willThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, User>(error)
        Assert.assertEquals(
            expectedResult,
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `load paging with null response` () = runBlockingTest {
        given(api.getUsers(any(), any())).willReturn(null)

        val expectedResult = PagingSource.LoadResult.Error<Int, User>(NullPointerException())
         Assert.assertEquals(
             expectedResult.toString(), pagingSource.load(
                 PagingSource.LoadParams.Refresh(
                     key = 0,
                     loadSize = 1,
                     placeholdersEnabled = false
                 )
             )
         )
    }

    @Test
    fun `load first page with success`() = runBlockingTest {
        given(api.getUsers(any(), any())).willReturn(userResponse)
        val expectedResult = userResponse.body()?.let {
            PagingSource.LoadResult.Page(
                data = it,
                prevKey = null,
                nextKey = 1
            )
        }

        Assert.assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `loading next page with success`() = runBlockingTest {
        given(api.getUsers(any(), any())).willReturn(nextUserResponse)

        val expectedResult =
            PagingSource.LoadResult.Page(
                data = userResponse.body()!!,
                prevKey = 1,
                nextKey = 2
            )


        Assert.assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `reviews paging source prepend - success`() = runBlockingTest {
        given(api.getUsers(any(), any())).willReturn(userResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = userResponse.body()!!,
            prevKey = null,
            nextKey = 1
        )
        Assert.assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    companion object {
        val userResponse: Response<List<User>> = Response.success(listOf(User(id = 1, name= "Sandrine Spinka",img ="https://randomuser.me/api/portraits/men/1.jpg", username= "Tod86")))

        val nextUserResponse: Response<List<User>> = Response.success(listOf(User(id = 4, name= "Mrs. Hilton Welch",img ="https://randomuser.me/api/portraits/men/4.jpg", username= "Tatyana_Ullrich")))
    }

}