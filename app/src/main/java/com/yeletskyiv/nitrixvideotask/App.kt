package com.yeletskyiv.nitrixvideotask

import android.app.Application
import android.content.Context
import com.yeletskyiv.nitrixvideotask.room.roomModule
import com.yeletskyiv.nitrixvideotask.retrofit.networkModule
import com.yeletskyiv.nitrixvideotask.viemodel.viewModelModule
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module

class App : Application() {

    private val appModule = module {
        getContext()
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, viewModelModule, networkModule, roomModule))
    }

    private fun getContext(): Context = applicationContext
}