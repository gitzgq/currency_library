package com.example.myapplication.net

import com.example.myapplication.HomepageBannerBean
import com.zgq.common.base.data.ZBaseBean
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitService {
    /**
     * 注：
     * 使用@Part、@PartMap,需要使用 @Multipart 标记
     * 使用@Field、@FieldMap,需要使用 @FormUrlEncoded 标记
     */

    @GET("Home/Banner")
    fun load(): Observable<ZBaseBean<ArrayList<HomepageBannerBean>>>
}