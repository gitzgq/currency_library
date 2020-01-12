package com.example.myapplication.net.response

import com.example.myapplication.bean.ZBaseBean
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
                        return@let
                    }
                    t.model?.let {
                        onSuccess(it, code)
                        return@let
                    }
                    t.list?.let {
                        onSuccess(it, code)
                        return@let
                    }
                    t.imageUri?.let {
                        onSuccess(it, code)
                        return@let
                    }
                    // code成功但是返回的 data、model、list、imageUri 是空
                    onEmptyData(code, t.message)
                }
                // 失败
                else ->{
                    onError(code, t.message)
                }
            }
            return@let
        }
        onError(-1, "获取数据异常")
    }

    override fun onError(e: Throwable) {
        onError(-2, e.message)
    }

    /** 获取Disposable对象 */
    abstract fun onDisposable(d: Disposable)

    /** 成功-有数据实体 */
    abstract fun onSuccess(t: T, code: Int)

    /** 成功-没有数据实体 */
    abstract fun onEmptyData(code: Int, msg: String?)

    /** 失败 */
    abstract fun onError(code: Int, msg: String?)

}