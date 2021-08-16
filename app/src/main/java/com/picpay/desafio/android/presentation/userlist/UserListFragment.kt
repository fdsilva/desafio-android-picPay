package com.picpay.desafio.android.presentation.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.empty_list_layout.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<UserListViewModel>()
    private val adapter by lazy {
        UserListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ActivityMainBinding.inflate(inflater).apply {
        binding = this
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservables()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.emptyListLayout.btnRetryEmpty.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setupObservables() {
        with(viewModel) {
            lifecycleScope.launch {
                getUserList().collectLatest {
                    adapter.submitData(it) }
                }
        }
    }

    private fun setupViews() {
        with(binding) {
            recyclerView.adapter = adapter.withLoadStateFooter(
                UserListLoadStateAdapter { adapter.retry() }
            )
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter.addLoadStateListener {
                emptyListLayout.isVisible = it.mediator?.refresh is LoadState.Error && adapter.itemCount == 0
                recyclerView.isVisible = it.refresh is LoadState.NotLoading || adapter.itemCount > 0
                binding.progressBar.isVisible = it.refresh is LoadState.Loading
            }
        }
    }

    companion object {
        fun getInstance() = UserListFragment()
    }
}