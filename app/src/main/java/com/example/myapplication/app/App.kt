package com.example.myapplication.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.myapplication.net.RetrofitService

class App : Application(){

    companion object{
        var service : RetrofitService? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        AppUtil.instence.init()
    }
}