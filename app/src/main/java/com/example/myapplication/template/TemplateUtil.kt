package com.example.myapplication.template

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.bean.Template101Bean
import com.zgq.common.base.other.ZLog

class TemplateUtil {
    companion object{
        val instence : TemplateUtil by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){ TemplateUtil() }

        /** 模板ID - 底部加载更多 */
        const val T_100 : Int = 100
        /** 模板ID - 测试 */
        const val T_101 : Int = 101
    }



    /**
     * 获取模板id
     */
    fun getTemplateId(any : Any?) : Int{
        if(null == any){
            return 0
        }
        when(any){
            any as? Template101Bean -> return any.templateId
        }
        return 0
    }

    /**
     * 获取模板id对应的模板View
     */
    fun getContentView(context: Context?, itemType : Int, inflater : LayoutInflater?) : View? {
        if(null == context){
            return null
        }
        if(null == inflater){
            return View(context)
        }
        when(itemType){
            T_100 -> return getTemplateView(R.layout.template_more_view_layout, inflater)
            T_101 -> return getTemplateView(R.layout.template_101_view_layout, inflater)
        }
        return View(context)
    }

    private fun getTemplateView(viewId : Int, inflater: LayoutInflater) : View?{
        if(null == inflater){
            return null
        }
        return inflater.inflate(viewId, null)
    }
}