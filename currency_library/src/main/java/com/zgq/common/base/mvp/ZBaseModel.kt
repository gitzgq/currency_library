package com.zgq.common.base.mvp

import io.reactivex.disposables.CompositeDisposable

open class ZBaseModel(basePresenter: ZBasePresenter<*>) {

    var disposable: CompositeDisposable? = null

    init {
        disposable = CompositeDisposable()
    }

    open fun clearNetWork() {
        if (null != disposable) {
            disposable?.clear()
            disposable = null
        }
    }
}