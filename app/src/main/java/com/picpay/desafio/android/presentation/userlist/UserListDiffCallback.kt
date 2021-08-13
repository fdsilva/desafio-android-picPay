package com.picpay.desafio.android.presentation.userlist

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.model.User

class UserListDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}