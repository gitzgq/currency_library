package com.example.myapplication.net

import com.example.myapplication.bean.Template101Bean
import com.zgq.common.base.data.ZBaseBean
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitService {
    /**
     * 注：
     * 使用@Part、@PartMap,需要使用 @Multipart 标记
     * 使用@Field、@FieldMap,需要使用 @FormUrlEncoded 标记
     */

    @GET(RequestUrl.URL_HOME_PAGE_BANNER)
    fun loadBanner(): Observable<ZBaseBean<MutableList<Template101Bean>>>
}