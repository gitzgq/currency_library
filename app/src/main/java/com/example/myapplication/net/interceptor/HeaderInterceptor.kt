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
        newRequest.header("token", "8C75AE030E53F29D7335F804C2797EF97EDF1F7832801E105CE046F28E18FD340189FA1DE269034A1B89C3B3DA782EB7E969A05BDF78154CC2EE76E82597BEC11496B7C1EB0DCCB7ECB4B859001A767360E28279C1AF695D63EFA65E3F6E223F3E6B2AB85703CA2E5944AB6443A75355BF9FA329572BD7B8115F2C8F7ACD33ED")

        return chain.proceed(newRequest.build())
    }
}