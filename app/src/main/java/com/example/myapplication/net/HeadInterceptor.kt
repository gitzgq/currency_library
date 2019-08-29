package com.example.myapplication.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder : Request.Builder = chain.request().newBuilder()
        // 来源
        builder.header("Userpoint", "")
        // 版本
        builder.header("app_version", "")
        // token
        builder.header("token", "")
        return  chain.proceed(builder.build())
    }

}