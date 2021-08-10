package com.picpay.desafio.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presentation.userlist.UserListFragment

class MainActivity : AppCompatActivity(R.layout.main_layout) {
    private val userFragment by lazy {
        UserListFragment.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        supportFragmentManager.beginTransaction().add(R.id.rootLayout, userFragment).commit()
    }
}
