package com.zgq.common.base.base_mvp

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