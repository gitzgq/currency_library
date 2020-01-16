package com.example.myapplication.net.request

import com.zgq.common.base.retrofit.parameter.ZJSONUtil
import com.zgq.common.base.retrofit.parameter.ZRequestParameter
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * 接口请求管理类
 */
object ZRequestBody {

    /***
     * 商品列表
     */
    fun loadGoodsList(pageIndex: Int): RequestBody{
        val obj = JSONObject()
        ZJSONUtil.put(obj, "districtId", "110105105")
        ZJSONUtil.put(obj, "pageSize", "10")
        ZJSONUtil.put(obj, "pageIndex", pageIndex)
        return ZRequestParameter.instence.body(obj.toString())
    }


}