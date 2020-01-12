package com.zgq.common.base.mvp

import java.lang.ref.WeakReference

/**
 * MVP - presenter基类
 */
open class ZBasePresenter<V : ZBaseView>(view: ZBaseView) {

    private val weakReference: WeakReference<*> = WeakReference(view)

    open fun getView(): V? {
        return weakReference.get() as V
    }

    open fun clear(){
        weakReference.clear()
    }

}