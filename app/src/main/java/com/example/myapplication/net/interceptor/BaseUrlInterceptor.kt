package com.example.myapplication.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 动态修改baseUrl
 */
class BaseUrlInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
//        //获取request的创建者builder
//        val builder = request.newBuilder()
//        //从request中获取headers，通过给定的键url_name
//        val headerValues = request.headers(DomainNameUrl.BASE_URL_SIGN)
//        if (headerValues != null && !headerValues.isEmpty()) { //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
//            builder.removeHeader(DomainNameUrl.BASE_URL_SIGN)
//            //匹配获得新的BaseUrl
//            val headerValue = headerValues[0]
//            if (!StringUtil.isEmpty(headerValue)) { //从request中获取原有的HttpUrl实例oldHttpUrl
//                val oldHttpUrl = request.url()
//                LogUtil.e("SIGN = $headerValue")
//                LogUtil.e("oldHttpUrl = $oldHttpUrl")
//                val baseUrl: HttpUrl?
//                // 根据标识判断使用对应域名
//                baseUrl = if (DomainNameUrl.SIGN_USER.equals(headerValue)) { // 更换为用户域名
//                    HttpUrl.parse(CtyConfig.BASE_USER_URL)
//                } else if (DomainNameUrl.SIGN_IMG_VIN.equals(headerValue)) { // 更换为上传行驶证或非营业执照图片域名
//                    HttpUrl.parse(CtyConfig.BASE_IMG_VIN_URL)
//                } else if (DomainNameUrl.SIGN_VIN_QUERY.equals(headerValue)) { // 更换VIN查询域名
//                    HttpUrl.parse(CtyConfig.BASE_VIN_QUERY_URL)
//                } else if (DomainNameUrl.SIGN_STORE.equals(headerValue)) { // 更换为商城域名
//                    HttpUrl.parse(CtyConfig.BASE_STORE_URL)
//                } else {
//                    oldHttpUrl
//                }
//                //重建新的HttpUrl，修改需要修改的url部分
//                val builderUrl = oldHttpUrl.newBuilder()
//                    .scheme(baseUrl!!.scheme()) //更换网络协议
//                    .host(baseUrl.host()) //更换主机名
//                    .port(baseUrl.port()) //更换端口;
//                if (MyApplication.sChannel.equals(CtyConfig.CS_B_TEST2_CTY001)) { // test的老的baseUrl后面多了一个参数 test2，需要删除
//                    builderUrl.removePathSegment(0)
//                }
//                LogUtil.e("newHttpUrl = $baseUrl")
//                LogUtil.e("baseUrl.scheme() = " + baseUrl.scheme())
//                LogUtil.e("baseUrl.host() = " + baseUrl.host())
//                LogUtil.e("baseUrl.port() = " + baseUrl.port())
//                //重建这个request，通过builder.url(newFullUrl).build()；
//                // 然后返回一个response至此结束修改
//                return chain.proceed(builder.url(builderUrl.build()).build())
//            }
//        }
        return chain.proceed(request)
    }
}