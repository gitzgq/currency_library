package com.zgq.common.base.mvp

import io.reactivex.disposables.CompositeDisposable

/**
 * MVP - model基类
 */
open class ZBaseModel(basePresenter: ZBasePresenter<*>) {

    val disposable: CompositeDisposable = CompositeDisposable()

    open fun clearNetWork() {
        disposable.clear()
    }
}