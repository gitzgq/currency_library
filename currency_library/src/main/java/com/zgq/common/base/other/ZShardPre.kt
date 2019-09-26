package com.zgq.common.base.other

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences 管理类
 */
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

    /**
     * 获取Int类型的数据
     */
    fun getInt(context: Context?, key : String) : Int{
        return getInt(context, key, 0)
    }

    /**
     * 获取Int类型的数据
     */
    fun getInt(context: Context?, key : String, defaultValue : Int) : Int{
        if(null == context || ZStringUtil.isEmpty(key)){
            return 0
        }
        val preferences = preferences(context)
        return preferences?.getInt(key, defaultValue) ?: 0
    }

    /**
     * 获取Long类型的数据
     */
    fun getLong(context: Context?, key : String) : Long{
        return getLong(context, key, 0)
    }

    /**
     * 获取Long类型的数据
     */
    fun getLong(context: Context?, key : String, defaultValue : Long) : Long{
        if(null == context || ZStringUtil.isEmpty(key)){
            return 0
        }
        val preferences = preferences(context)
        return preferences?.getLong(key, defaultValue) ?: 0
    }

    /**
     * 获取Boolean类型的数据
     */
    fun getBoolean(context: Context?, key : String) : Boolean{
        return getBoolean(context, key, false)
    }

    /**
     * 获取Boolean类型的数据
     */
    fun getBoolean(context: Context?, key : String, defaultValue : Boolean) : Boolean{
        if(null == context || ZStringUtil.isEmpty(key)){
            return false
        }
        val preferences = preferences(context)
        return preferences?.getBoolean(key, defaultValue) ?: false
    }

    /**
     * 获取Float类型的数据
     */
    fun getFloat(context: Context?, key : String) : Float{
        return getFloat(context, key, 0.0F)
    }

    /**
     * 获取Float类型的数据
     */
    fun getFloat(context: Context?, key : String, defaultValue : Float) : Float{
        if(null == context || ZStringUtil.isEmpty(key)){
            return 0.0F
        }
        val preferences = preferences(context)
        return preferences?.getFloat(key, defaultValue) ?: 0.0F
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
