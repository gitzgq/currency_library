package com.zgq.common.base.other

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * 吐司  管理类
 */
object ZToast {

    private var toast : Toast? = null

    fun toast(context: Context?, content : String?){
        toast(context, content, Toast.LENGTH_SHORT)
    }

    fun toast(context: Context?, content : String?, time : Int){
        if(null == content || ZStringUtil.isEmpty(content)){
            return
        }
        cancel()
        toast = Toast.makeText(context, content, time)
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.setText(content)
        toast?.show()
    }

    fun cancel(){
        toast?.cancel()
        toast = null
    }

}