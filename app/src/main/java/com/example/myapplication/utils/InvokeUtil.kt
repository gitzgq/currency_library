package com.example.myapplication.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zgq.common.base.other.ZStringUtil
import com.zgq.common.base.other.ZToast

/**
 * 页面跳转管理类
 */
class InvokeUtil {

    companion object{
        val instance: InvokeUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { InvokeUtil() }

        /** 跳转到RecyclerView列表 Activity */
        const val RECYCLERVIEW_LIST = "com.example.myapplication.mvp.view.RecyclerViewActivity"
    }

    fun invoke(context: Context, path: String, bundle: Bundle? = null){
        if(ZStringUtil.isEmpty(path)){
            ZToast.toast(context, "跳转路径不正确")
            return
        }
        val intent = Intent(context, Class.forName(path))
        bundle?.let {
            intent.putExtras(it)
        }
        context.startActivity(intent)
    }

}