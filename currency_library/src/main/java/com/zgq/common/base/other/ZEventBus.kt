package com.zgq.common.base.other

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

/**
 * EventBus 管理类
 */
class ZEventBus {

    companion object{
        val instence : ZEventBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ZEventBus() }
    }

    /**
     * 注册
     */
    fun register(any : Any){
        any?.let {
            if(it is AppCompatActivity || it is Fragment){
                EventBus.getDefault().register(it)
            }
        }
    }

    /**
     * 反注册
     */
    fun unregister(any : Any){
        any?.let {
            if(it is AppCompatActivity || it is Fragment){
                EventBus.getDefault().unregister(it)
            }
        }
    }


    /**
     * 发送普通事件
     */
    fun post(any: Any?){
        any?.let {
            EventBus.getDefault().post(it)
        }

    }

    /**
     * 发送普通事件
     */
    fun postSticky(any: Any?){
        any?.let {
            EventBus.getDefault().postSticky(it)
        }
    }
}