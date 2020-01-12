package com.example.myapplication.app

import com.zgq.common.base.app.ZBaseApplication

class App : ZBaseApplication(){

    override fun onCreate() {
        super.onCreate()
        ConfigUtil.init()
    }
}