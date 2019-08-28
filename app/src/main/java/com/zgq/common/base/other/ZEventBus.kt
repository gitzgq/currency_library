package com.zgq.common.base.other

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus
import java.util.*

class ZEventBus {
    companion object{
        val instence : ZEventBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ZEventBus() }

        /** 事件ID-测试 */
        val E_100 : Int = 100
    }

    /**
     * 注册
     */
    fun register(subscriber : Any){
        if(null == subscriber || (subscriber !is AppCompatActivity && subscriber !is Fragment)){
            ZLog.e("注册EventBus失败")
            return
        }
        EventBus.getDefault().register(subscriber)
    }

    /**
     * 反注册
     */
    fun unregister(subscriber : Any){
        if(null == subscriber || (subscriber !is AppCompatActivity && subscriber !is Fragment)){
            ZLog.e("反注册EventBus失败")
            return
        }
        EventBus.getDefault().unregister(subscriber)
    }


    /**
     * 发送普通事件
     */
    fun post(any: Any){
        if(null == any){
            ZLog.e("EventBus发送普通事件失败")
            return
        }
        EventBus.getDefault().post(any)
    }

    /**
     * 发送普通事件
     */
    fun postSticky(any: Any){
        if(null == any){
            ZLog.e("EventBus发送粘性事件失败")
            return
        }
        EventBus.getDefault().postSticky(any)
    }
}