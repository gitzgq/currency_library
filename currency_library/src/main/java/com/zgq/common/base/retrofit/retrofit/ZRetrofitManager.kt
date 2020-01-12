package com.zgq.common.base.retrofit.retrofit

import com.zgq.common.base.other.ZLog
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ZRetrofitManager {

    // 声明retrofit
    lateinit var retrofit: Retrofit
    // 默认打印网络请求log
    private var logDebug: Boolean = true

    // 单例
    companion object {
        val instance: ZRetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZRetrofitManager()
        }
    }

    /**
     * 初始化Retrofit
     * time 秒
     * interceptor 公共参数拦截器
     * baseUrl 接口域名
     */
    fun init(
            baseUrl: String,
            okHttpClient: OkHttpClient
    ): ZRetrofitManager {
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return instance
    }

    fun getOKHttp(): OkHttpClient {
        return getOKHttp(60, null, null)
    }

    /** 初始化OKHttp
     * time 连接、写入、读取时间-秒
     * publicInterceptor 公共参数拦截器
     * baseUrlInterceptor 动态修改baseUrl拦截器
     * */
    fun getOKHttp(
            time: Long,
            publicInterceptor: Interceptor?,
            baseUrlInterceptor: Interceptor?
    ): OkHttpClient {
        // 初始化网络日志打印
        val httpLogger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                ZLog.e("ZLOG = $message")
            }
        })
        // 设置级别最高（全部都打印）
        httpLogger?.level =
                if (logDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient()
                .newBuilder()
                .let {
                    it.readTimeout(time, TimeUnit.SECONDS)
                    it.writeTimeout(time, TimeUnit.SECONDS)
                    it.connectTimeout(time, TimeUnit.SECONDS)
                    // 添加log拦截器
                    it.addInterceptor(httpLogger)
                    // 动态改变baseUrl拦截器
                    baseUrlInterceptor?.let { baseUrlInterceptor ->
                        it.addInterceptor(baseUrlInterceptor)
                    }
                    // 添加头部拦截器
                    publicInterceptor?.let { publicInterceptor ->
                        it.addNetworkInterceptor(publicInterceptor)
                    }
                    // 错误重试
                    it.retryOnConnectionFailure(true)
                    it.build()
                }

    }

    /** 获取注解接口 */
    fun <T> getService(cLass: Class<T>): T {
        return retrofit.create(cLass)
    }

    /** 获取Retrofit实例 */
    fun retrofit(): Retrofit {
        return retrofit
    }

    /** 设置log打印 true：打印  false：不打印 */
    fun setDebug(debug: Boolean) {
        logDebug = debug
    }

}