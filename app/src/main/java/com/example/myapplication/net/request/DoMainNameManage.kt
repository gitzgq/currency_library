package com.example.myapplication.net.request

/**
 * 接口域名管理类
 */
object DoMainNameManage {

    /** 域名的headers 标识 - 通过拦截器进行判断动态修改baseUrl  */
    const val BASE_URL_SIGN = "cty_base_url"

    /** 商城 - 域名的headers 标识 - 在拦截器中进行判断  */
    const val SIGN_STORE = "store"
    /** 商城 - 域名的headers 标识 - 添加ApiUrl 请求方法上面  */
    const val BASE_URL_STORE_SIGN: String =  "$BASE_URL_SIGN:$SIGN_STORE"

    /** 商城-baseUrl-测试1 */
    const val STORE_TEST1: String = "http://test1-ysj.autocloudpro.com/"

    /** 商城-baseUrl-UAT */
    const val STORE_UAT: String = "http://uat-ysj.autocloudpro.com/"



}