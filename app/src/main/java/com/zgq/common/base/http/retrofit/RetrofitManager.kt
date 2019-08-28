package com.zgq.common.base.http.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.zgq.common.base.other.ZLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager{

    // 网络请求设置超时时间
    private val DEFAULT_TIME : Long = 30

    /** 设置OkHttp */
    private fun okHttp() : OkHttpClient{
        // 初始化网络日志打印
        val httpLogger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                ZLog.e("HttpLog = ", message)
            }
        })
        // 设置级别最高（全部都打印）
        httpLogger?.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient()
            .newBuilder()
            .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
            .addInterceptor(httpLogger)// 添加log拦截器
            .addNetworkInterceptor(HeadInterceptor())// 添加头部拦截器
            .retryOnConnectionFailure(true)// 错误重试
            .build()
    }

    /** 初始化Retrofit */
    private fun retrofit() : Retrofit{
        return Retrofit
            .Builder()
            .client(okHttp())
            .baseUrl("http://newapi.autocloudpro.com/dev/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofit : Retrofit = retrofit()

    /** 获取注解接口 */
    fun <T> getService(cLass : Class<T>) : T {
       return retrofit.create(cLass)
    }

}