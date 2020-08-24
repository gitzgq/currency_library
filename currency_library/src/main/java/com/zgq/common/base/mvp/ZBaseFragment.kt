package com.zgq.common.base.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zgq.common.base.other.ZEventBus

/**
 * Fragment的基类
 */
open abstract class ZBaseFragment<P : ZBasePresenter<*>> : Fragment(), ZBaseView {

    /** Presenter */
    var mPresenter: P? = null

    var context = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as Nothing?
        mPresenter = getPresenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentView, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        // true：注册EventBus  默认false
        if(registerEventBus()){
            ZEventBus.instence.register(this)
        }
    }

    /** 获取需要加载的布局文件 */
    abstract val contentView : Int

    /** 获取Presenter实例 */
    abstract val getPresenter: P

    /** 初始化view、数据等 */
    override fun initView() {
    }

    /** 显示加载框 */
    override fun showLoading() {
    }

    /** 显示UI */
    override fun showData() {
    }

    /** 显示无数据页面 */
    override fun showEmpty() {
    }

    /** 显示错误页面 */
    override fun showError() {
    }

    /*** 是否使用EventBus true：使用   默认false */
    open fun registerEventBus() : Boolean{
        return false
    }

    /** 发送普通事件 */
    open fun post(any: Any?){
        ZEventBus.instence.post(any)
    }

    /** 发送粘性事件 */
    open fun postSticky(any: Any?){
        ZEventBus.instence.postSticky(any)
    }

    override fun onDestroy() {
        mPresenter?.clear()
        // true：注册EventBus  默认false
        if(registerEventBus()){
            ZEventBus.instence.unregister(this)
        }
        super.onDestroy()
    }

}