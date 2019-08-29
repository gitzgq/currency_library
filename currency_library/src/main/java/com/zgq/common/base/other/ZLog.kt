package com.zgq.common.base.other

import android.util.Log

/**
 * 日志打印管理类
 */
object ZLog {
    private val TAG: String = "ZLog"
    private var isDebug : Boolean = true

    fun setDebug(b : Boolean){
        isDebug = b
    }

    fun e(content: String?) {
        e(TAG, content)
    }

    fun e(tag: String?, content: String?) {
        if(isDebug){
            Log.e(tag, content)
        }
    }
}