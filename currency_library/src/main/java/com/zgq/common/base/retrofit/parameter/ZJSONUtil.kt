package com.zgq.common.base.retrofit.parameter

import com.zgq.common.base.other.ZStringUtil
import org.json.JSONArray
import org.json.JSONObject

/**
 * JSON拼接管理类
 */
class ZJSONUtil {
    companion object {
        val instence: ZJSONUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZJSONUtil()
        }
    }

    /**
     * 拼接JSONObject
     */
    fun put(o : JSONObject, key : String, value : Any) {
        if (null == o || ZStringUtil.isEmpty(key) || null == value) {
            return
        }
        o.put(key, value)
        return
    }

    /**
     * 拼接JSONArray
     */
    fun put(a : JSONArray, value : Any){
        if (null == a || null == value) {
            return
        }
        put(a, value)
    }

    /**
     * 判断是否包含这个key，并获取key对应的值
     */
    fun hasObject(jsonObjec: JSONObject?, key : String) : JSONObject{
        if(isHas(jsonObjec, key)){
            return jsonObjec?.getJSONObject(key) ?: JSONObject()
        }
        return JSONObject()
    }

    fun hasString(jsonObjec: JSONObject?, key : String) : String{
        if(isHas(jsonObjec, key)){
            return jsonObjec?.getString(key) ?: ""
        }
        return ""
    }

    fun hasInt(jsonObjec: JSONObject?, key : String) : Int{
        if(isHas(jsonObjec, key)){
            return jsonObjec?.getInt(key) ?: 0
        }
        return 0
    }

    fun hasDouble(jsonObjec: JSONObject?, key : String) : Double{
        if(isHas(jsonObjec, key)){
            return jsonObjec?.getDouble(key) ?: 0.0
        }
        return 0.0
    }

    fun hasBoolean(jsonObjec: JSONObject?, key : String) : Boolean{
        if(isHas(jsonObjec, key)){
            return jsonObjec?.getBoolean(key) ?: false
        }
        return false
    }

    fun hasLong(jsonObjec: JSONObject?, key : String) : Long{
        if(isHas(jsonObjec, key)){
            return jsonObjec?.getLong(key) ?: 0
        }
        return 0
    }

    /**
     * 判断是否包含这个key
     */
    fun isHas(jsonObjec: JSONObject?, key : String): Boolean {
        return jsonObjec != null && !ZStringUtil.isEmpty(key) && jsonObjec.has(key)
    }

}