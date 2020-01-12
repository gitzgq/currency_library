package com.example.myapplication.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加公共参数
 */
class HeaderInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
        // 公共参数
//        newRequest.header("Userpoint", RequestBodyUtil.build().source(false))

        return chain.proceed(newRequest.build())
    }
}