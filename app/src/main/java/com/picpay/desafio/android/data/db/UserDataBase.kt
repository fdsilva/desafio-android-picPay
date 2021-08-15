package com.picpay.desafio.android.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.model.RemoteKeys
import com.picpay.desafio.android.model.User

@Database(
    entities = [User::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class UserDataBase : RoomDatabase() {
   abstract fun usersDao(): UserDao
   abstract fun remoteKeysDao(): RemoteKeysDao

   companion object {
       @Volatile
       private var INSTANCE: UserDataBase? = null

       fun getInstance(context: Context): UserDataBase =
           INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also{
                INSTANCE = it
            }
       }

       private fun buildDatabase(context: Context) =
           Room.databaseBuilder(context.applicationContext,
               UserDataBase::class.java, "PicPay.db")
               .build()
   }
}