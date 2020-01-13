package com.example.myapplication.net.response

import com.example.myapplication.bean.ZBaseBean
import com.zgq.common.base.other.ZLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 网络请求-统一处理下发的code、msg、data
 */
abstract class BaseObserver<T>: Observer<ZBaseBean<T>>{

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        onDisposable(d)
    }

    override fun onNext(t: ZBaseBean<T>) {
        t?.let { t ->
            when(val code: Int = t.code){
                // 成功
                ResultCodeUtil.CODE_0 ->{
                    t.data?.let {
                        onSuccess(it, code)
                        return
                    }
                    t.model?.let {
                        onSuccess(it, code)
                        return
                    }
                    t.list?.let {
                        onSuccess(it, code)
                        return
                    }
                    t.imageUri?.let {
                        onSuccess(it, code)
                        return
                    }
                    t.customEmptyData?.let {
                        onSuccess(it, code)
                        return
                    }
                    ZLog.e("网络请求下发成功code = $code")
                }
                // 失败
                else ->{
                    ZLog.e("网络请求下发错误code = $code")
                    onError(code, t.message)
                }
            }
            return
        }
        ZLog.e("网络请求下发数据异常 = -1")
        onError(-1, "获取数据异常")
    }

    override fun onError(e: Throwable) {
        ZLog.e("网络请求异常 = ${e.message}")
        onError(-2, e.message)
    }

    /** 获取Disposable对象 */
    abstract fun onDisposable(d: Disposable)

    /** 成功 */
    abstract fun onSuccess(t: T, code: Int)

    /** 失败 */
    abstract fun onError(code: Int, msg: String?)

}