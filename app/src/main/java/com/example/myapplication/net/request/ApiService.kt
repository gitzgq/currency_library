package com.example.myapplication.net.request

import com.example.myapplication.bean.Template101Bean
import com.example.myapplication.bean.ZBaseBean
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    /**
     * 注：
     * 使用@Part、@PartMap,需要使用 @Multipart 标记
     * 使用@Field、@FieldMap,需要使用 @FormUrlEncoded 标记
     */

    @GET(RequestUrl.URL_HOME_PAGE_BANNER)
    fun loadBanner(): Observable<ZBaseBean<ArrayList<Template101Bean>>>

}