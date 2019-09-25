package com.example.myapplication.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder : Request.Builder = chain.request().newBuilder()
        // 来源
        builder.header("Userpoint", "4")
        // 版本
        builder.header("app_version", "1.1.0")
        // token
        builder.header("token", "vlpHLoUHQuagv6XamteRpmFKjKHmOiGpqziPPbBRBN3lDXEk5SFpuCzBmvP/MP7QCgxI4W7SGF615DTqaOCEMu7pJ+P3CuMbvY5HfWY/AbZRK39AgDvIa4lM7p6rg7KY")
        return  chain.proceed(builder.build())
    }

}