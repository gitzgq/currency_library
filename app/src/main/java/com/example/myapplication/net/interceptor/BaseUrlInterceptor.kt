package com.example.myapplication.net.interceptor

import com.example.myapplication.net.request.DoMainNameManage
import com.zgq.common.base.other.ZStringUtil
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 动态修改baseUrl
 */
class BaseUrlInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        //获取request的创建者builder
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        request.headers(DoMainNameManage.BASE_URL_SIGN)?.let {
            if(it.isNotEmpty()){
                builder.removeHeader(DoMainNameManage.BASE_URL_SIGN)
                //匹配获得新的BaseUrl
                val headerValue = it[0]
                if (!ZStringUtil.isEmpty(headerValue)) { //从request中获取原有的HttpUrl实例oldHttpUrl
                    val oldHttpUrl = request.url
                    // 根据标识判断使用对应域名
                    val baseUrl: HttpUrl? = if (DoMainNameManage.SIGN_STORE == headerValue) { // 更换为商城域名
                        DoMainNameManage.STORE_UAT.toHttpUrlOrNull()
                    } else {
                        oldHttpUrl
                    }
                    baseUrl?.let { url ->
                        //重建新的HttpUrl，修改需要修改的url部分
                        val builderUrl = oldHttpUrl.newBuilder()
                                .scheme(url.scheme) //更换网络协议
                                .host(url.host) //更换主机名
                                .port(url.port) //更换端口;
//                if (MyApplication.sChannel.equals(CtyConfig.CS_B_TEST2_CTY001)) { // test的老的baseUrl后面多了一个参数 test2，需要删除
//                    builderUrl.removePathSegment(0)
//                }
                        //重建这个request，通过builder.url(newFullUrl).build()；
                        // 然后返回一个response至此结束修改
                        return chain.proceed(builder.url(builderUrl.build()).build())
                    }
                }
            }
        }
        return chain.proceed(request)
    }
}