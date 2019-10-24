package com.zgq.common.base.mvp

interface ZBaseView {
    /** 初始化数据、view等 */
    fun initView()
    /** 显示加载框 */
    fun showLoading()
    /** 显示页面数据 */
    fun showData()
    /** 显示空页面 */
    fun showEmpty()
    /** 显示错误页面 */
    fun showError()
}