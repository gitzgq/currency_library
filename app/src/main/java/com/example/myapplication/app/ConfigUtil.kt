package com.example.myapplication.app

import com.example.myapplication.net.interceptor.HeaderInterceptor
import com.example.myapplication.R
import com.example.myapplication.net.interceptor.BaseUrlInterceptor
import com.example.myapplication.net.request.ApiService
import com.example.myapplication.net.request.RequestUrl
import com.zgq.common.base.retrofit.retrofit.ZRetrofitManager
import com.zgq.common.base.other.ZImmersionBar

/**
 * 项目配置管理类
 */
object ConfigUtil {

    lateinit var service: ApiService

    fun init() {
        initRetrofit()
        ZImmersionBar.statusColor = R.color.colorAccent
    }

    // 初始化网络请求
    private fun initRetrofit() {
        // 获取接口类
        service = ZRetrofitManager.instance
                .init(RequestUrl.BASE_URL, ZRetrofitManager.instance.getOKHttp(60, HeaderInterceptor(), BaseUrlInterceptor()))
                .getService(ApiService::class.java)
    }
}