package com.zgq.common.base.retrofit.result

import com.zgq.common.base.data.ZBaseBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 接收下发的数据 统一管理类
 */
abstract class BaseObserver<T> : Observer<ZBaseBean<T>> {

    override fun onSubscribe(d: Disposable) {
        disposable(d)
    }

    override fun onNext(baseBean: ZBaseBean<T>) {
        if (null == baseBean) {
            onError(-1, "下发的数据格式不正确")
            return
        }
        if (baseBean?.code == 0 || baseBean?.code == 1) {
            if (null != baseBean.model) {
                onSuccess(baseBean.model)
                return
            }
            if (null != baseBean.data) {
                onSuccess(baseBean.data)
                return
            }
            if (null != baseBean.imageUri) {
                onSuccess(baseBean.imageUri)
                return
            }
            onSuccess(null)
            return
        }
        onError(baseBean.code, baseBean.message)
    }

    override fun onError(e: Throwable) {
        onError(-1, e.message)
    }

    override fun onComplete() {

    }

    /** rxjava */
    abstract fun disposable(d: Disposable)

    /**
     * 获取数据成功
     * data 泛型
     */
    abstract fun onSuccess(data: T?)


    /** 获取数据失败 */
    abstract fun onError(code: Int, errorMsg: String?)

}