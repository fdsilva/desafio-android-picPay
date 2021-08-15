package com.picpay.desafio.android.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @field:SerializedName("img") val img: String,
    @field:SerializedName("name") val name: String,
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("username") val username: String
) : Parcelable