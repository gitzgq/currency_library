package com.zgq.common.base.other

import android.content.Context
import android.content.SharedPreferences
import com.zgq.common.base.app.ZBaseApplication

/**
 * SharedPreferences 管理类
 */
object ZShardPre {

    // 名称
    private val P_NAME = "zgq_cty_sp_db"

    /** 获取SharedPreferences实例 */
    fun preferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(P_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 添加数据
     * @param context
     * @param key       key
     * @param o         value
     */
    fun put(context: Context, key: String, o: Any) {
        if (ZStringUtil.isEmpty(key)) {
            return
        }
        preferences(context).edit()?.let {
            when (o) {
                is String -> it.putString(key, o)
                is Boolean -> it.putBoolean(key, o)
                is Int -> it.putInt(key, o)
                is Long -> it.putLong(key, o)
                is Float -> it.putFloat(key, o)
            }
            it.commit()
        }
    }

    /**
     * 获取String类型的数据
     */
    fun getString(context: Context, key: String, defaultValue: String = ""): String {
        if (ZStringUtil.isEmpty(key)) {
            return ""
        }
        return preferences(context).getString(key, defaultValue) ?: ""
    }

    /**
     * 获取Int类型的数据
     */
    fun getInt(context: Context, key: String, defaultValue: Int = 0): Int {
        if (ZStringUtil.isEmpty(key)) {
            return 0
        }
        return preferences(context).getInt(key, defaultValue)
    }

    /**
     * 获取Long类型的数据
     */
    fun getLong(context: Context, key: String, defaultValue: Long = 0): Long {
        if (ZStringUtil.isEmpty(key)) {
            return 0
        }
        return preferences(context).getLong(key, defaultValue)
    }

    /**
     * 获取Boolean类型的数据
     */
    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        if (ZStringUtil.isEmpty(key)) {
            return false
        }
        return preferences(context).getBoolean(key, defaultValue)
    }


    /**
     * 获取Float类型的数据
     */
    fun getFloat(context: Context, key: String, defaultValue: Float = 0.0F): Float {
        if (ZStringUtil.isEmpty(key)) {
            return 0.0F
        }
        return preferences(context).getFloat(key, defaultValue)
    }

    /**
     * 删除某个key对应的数据
     */
    fun remove(context: Context, key: String) {
        if (ZStringUtil.isEmpty(key)) {
            return
        }
        preferences(context).edit()?.let {
            it.remove(key).commit()
        }
    }

    /**
     * 清除数据
     */
    fun clear(context: Context) {
        preferences(context).edit()?.let {
            it.clear().commit()
        }
    }

}
