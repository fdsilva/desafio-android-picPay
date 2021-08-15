package com.picpay.desafio.android.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM users")
    fun getAllUsers(): PagingSource<Int, User>

    @Query("DELETE FROM users")
    suspend fun clearUsers()
}