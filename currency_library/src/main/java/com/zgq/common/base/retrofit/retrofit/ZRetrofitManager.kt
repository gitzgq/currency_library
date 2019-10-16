package com.zgq.common.base.retrofit.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.zgq.common.base.other.ZLog
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ZRetrofitManager{

    private var retrofit : Retrofit? = null

    /** 初始化Retrofit */
    fun retrofit(time : Long, interceptor: Interceptor, baseUrl : String) : Retrofit?{
        if(null != retrofit){
            return retrofit
        }
        // 初始化网络日志打印
        val httpLogger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                ZLog.e("ZLOG = $message")
            }
        })
        // 设置级别最高（全部都打印）
        httpLogger?.level = HttpLoggingInterceptor.Level.BODY
        retrofit = Retrofit
                        .Builder()
                        .client(// 设置okHttp
                            OkHttpClient()
                                .newBuilder()
                                .readTimeout(time, TimeUnit.SECONDS)
                                .writeTimeout(time, TimeUnit.SECONDS)
                                .connectTimeout(time, TimeUnit.SECONDS)
                                .addInterceptor(httpLogger)// 添加log拦截器
                                .addNetworkInterceptor(interceptor)// 添加头部拦截器
                                .retryOnConnectionFailure(true)// 错误重试
                                .build())
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
        return retrofit
    }

    /** 获取注解接口 */
    fun <T> getService(cLass : Class<T>) : T? {
       return retrofit?.create(cLass)
    }

}