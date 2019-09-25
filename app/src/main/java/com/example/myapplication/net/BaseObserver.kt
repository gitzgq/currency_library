package com.example.myapplication.net

import com.zgq.common.base.data.ZEventData
import com.zgq.common.base.other.ZEventBus
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.retrofit.result.ZBaseObserver
import io.reactivex.disposables.Disposable

/**
 * 接收下发的数据 统一管理类
 */
abstract class BaseObserver<T> : ZBaseObserver<T>() {

    override fun disposable(d: Disposable) {

    }

    override fun onSuccess(data: T?) {
        ZLog.e("走的不是这")
        ZEventBus.instence.post(ZEventData(500, "测试"))
    }

    override fun onError(code: Int, errorMsg: String?) {

    }
}