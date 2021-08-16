package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.di.dataModule
import com.picpay.desafio.android.data.di.networkModule
import com.picpay.desafio.android.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@PicPayApplication)
            modules(listOf(networkModule, dataModule, presentationModule))
        }
    }
}