package com.gb_materialdesign.app

import android.app.Application
import com.gb_materialdesign.di.networkModule
import com.gb_materialdesign.di.repositoryModule
import com.gb_materialdesign.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}