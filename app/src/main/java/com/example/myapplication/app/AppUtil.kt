package com.example.myapplication.app

import com.example.myapplication.R
import com.example.myapplication.net.HeadInterceptor
import com.example.myapplication.net.RequestUrl
import com.example.myapplication.net.RetrofitService
import com.zgq.common.base.retrofit.retrofit.ZRetrofitManager
import com.zgq.common.base.other.ZImmersionBar

class AppUtil {
    companion object{
        val instence : AppUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { AppUtil() }
    }

    fun init(){
        initRetrofit()
        ZImmersionBar.statusColor = R.color.colorAccent
    }

    // 初始化网络请求
    private fun initRetrofit(){
        // 初始化
        ZRetrofitManager.retrofit(30, HeadInterceptor(), RequestUrl.BASE_URL)
        // 获取接口类
        App.service = ZRetrofitManager.getService(RetrofitService:: class.java)
    }
}