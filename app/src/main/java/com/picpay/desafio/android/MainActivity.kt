package com.picpay.desafio.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<UserListViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViews()
        setupObservables()
        viewModel.getUserList()
    }

    private fun setupObservables() {
        with(viewModel) {
            userList.observe(this@MainActivity, Observer {
                adapter.users = it
            })
        }
    }

    private fun setupViews() {
        with(binding) {
            adapter = UserListAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
