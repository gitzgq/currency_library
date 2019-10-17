package com.zgq.common.base.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.zgq.common.base.other.ZEventBus
import com.zgq.common.base.other.ZImmersionBar
import com.zgq.common.base.other.ZToast

/**
 * Activity的基类
 */
open abstract class ZBaseActivity<P : ZBasePresenter<*>> : AppCompatActivity(), ZBaseView {

    /** Presenter */
    var mPresenter: P? = null
    /** 设置状态栏沉浸式 */
    private var immersionBar : ImmersionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(statusIsInit()){
            setStatusStyle()
        }
        setContentView(contentView)
        mPresenter = getPresenter
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

    /** 完全沉浸 */
    val TRANSPAREN : Int = -1
    /** 设置标题颜色 */
    val COLOR : Int = -2

    /** 返回不同的类型 */
    open fun statusType() : Int{
        return COLOR
    }

    /** 返回类型为COLOR时， 返回颜色值 */
    open fun statusColor() : Int{
        return ZImmersionBar.statusColor
    }

    /** 是否使用沉浸式 */
    open fun statusIsInit() : Boolean{
        return true
    }

    // 设置沉浸式状态栏
    private fun setStatusStyle(){
        if(null == immersionBar){
            immersionBar = ImmersionBar.with(this)
        }
        if(statusType() == TRANSPAREN){
            // 直接沉浸式（顶部的标题直接沉浸到状态栏）
            immersionBar?.transparentStatusBar()
        }else{
            // 设置状态栏的颜色
            immersionBar?.fitsSystemWindows(true)
            immersionBar?.statusBarColor(statusColor())
        }
        // 当状态栏背景为亮色，手机不支持修改状态栏的字体颜色时调用此方法
        immersionBar?.statusBarDarkFont(true)
        immersionBar?.init()
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

    override fun onPause() {
        ZToast.cancel()
        super.onPause()
    }

    override fun onDestroy() {
        if(null != mPresenter){
            mPresenter?.clear()
        }
        // true：注册EventBus  默认false
        if(registerEventBus()){
            ZEventBus.instence.unregister(this)
        }
        super.onDestroy()
    }

}