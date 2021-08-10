package com.picpay.desafio.android.presentation.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
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
        viewModel.getUserList()
    }

    private fun setupObservables() {
        with(viewModel) {
            userList.observe(viewLifecycleOwner, Observer {
                adapter.users = it
            })
        }
    }

    private fun setupViews() {
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        fun getInstance() = UserListFragment()
    }
}