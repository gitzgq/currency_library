package com.zgq.common.base.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

open class ZBaseApplication: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}