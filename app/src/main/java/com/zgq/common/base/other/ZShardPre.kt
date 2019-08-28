package com.zgq.common.base.other

import android.content.Context
import android.content.SharedPreferences

object ZShardPre{

    // 名称
    private val P_NAME = "zgq_cty_sp_db"

    /** retrofit的BaseUrl */
    val KEY_BASE_URL : String = "key_retrofit_base_url"
    /** 请求头部的版本 */
    val KEY_HEAD_VERSION : String = "key_head_version"
    /** 请求头部的token */
    val KEY_HEAD_TOKEN : String = "key_head_token"

    /**
     * 添加数据
     * @param context
     * @param key       key
     * @param o         value
     */
    fun put(context: Context?, key: String, o: Any?) {
        if (null == context || ZStringUtil.isEmpty(key) || null == o) {
            return
        }
        val preferences = preferences(context)
        val editor = preferences?.edit()
        when (o) {
            is String -> editor?.putString(key, (o as String?) ?: "")
            is Boolean -> editor?.putBoolean(key, (o as Boolean?) ?: false)
            is Int -> editor?.putInt(key, (o as Int?) ?: 0)
            is Long -> editor?.putLong(key, (o as Long?) ?: 0)
            is Float -> editor?.putFloat(key, (o as Float?) ?: 0f)
        }
        editor?.commit()
    }

    /**
     * 获取String类型的数据
     */
    fun getString(context: Context?, key : String) : String{
        return getString(context, key, "")
    }

    /**
     * 获取String类型的数据
     */
    fun getString(context: Context?, key : String, defaultValue : String) : String{
        if(null == context || ZStringUtil.isEmpty(key)){
            return ""
        }
        val preferences = preferences(context)
        return preferences?.getString(key, defaultValue) ?: ""
    }

    fun preferences(context: Context?) : SharedPreferences? {
        return context?.getSharedPreferences(P_NAME, Context.MODE_PRIVATE)?: null
    }

    /**
     * 清除数据
     */
    fun clear(context: Context?){
        val preferences = preferences(context)
        val editor = preferences?.edit()
        editor?.clear()
        editor?.commit()
    }

}
