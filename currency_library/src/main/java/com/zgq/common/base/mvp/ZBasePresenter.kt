package com.zgq.common.base.mvp

import java.lang.ref.WeakReference

open class ZBasePresenter<V : ZBaseView>(view: ZBaseView) {

    private var weakReference: WeakReference<*>? = null

    init {
        weakReference = WeakReference(view)
    }

    open fun getView(): V? {
        return if (null != weakReference) {
            weakReference?.get() as V
        } else null
    }

    open fun clear(){
        if(null != weakReference){
            weakReference?.clear()
        }
    }

}