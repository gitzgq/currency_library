package com.example.myapplication.net.request

import com.example.myapplication.bean.GoodsListBean
import com.example.myapplication.bean.Template101Bean
import com.example.myapplication.bean.ZBaseBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    /**
     * 注：
     * 使用@Part、@PartMap,需要使用 @Multipart 标记
     * 使用@Field、@FieldMap,需要使用 @FormUrlEncoded 标记
     */

    @GET(RequestUrl.URL_HOME_PAGE_BANNER)
    fun loadBanner(): Observable<ZBaseBean<ArrayList<Template101Bean>>>

    @Headers(DoMainNameManage.BASE_URL_STORE_SIGN)
    @POST(RequestUrl.URL_GOODS_LIST)
    fun loadGoodsList(@Body body: RequestBody): Observable<ZBaseBean<GoodsListBean>>

}