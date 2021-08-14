package com.picpay.desafio.android.presentation.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.LoadingRetryLayoutBinding

class UserListLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UserListLoadStateAdapter.LoadsStateViewHolder>(){

    class LoadsStateViewHolder(
        private val binding: LoadingRetryLayoutBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener {
                retry()
            }
        }

        fun binding(loadState: LoadState) {
            with(binding) {
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible = loadState is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: LoadsStateViewHolder,
                                  loadState: LoadState) = holder.binding(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = LoadsStateViewHolder(
        LoadingRetryLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), retry
    )
}