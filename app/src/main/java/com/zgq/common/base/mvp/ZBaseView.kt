package com.zgq.common.base.mvp

interface ZBaseView {
    fun initView()
    fun showLoading(){}
    fun showData(){}
    fun showEmpty(){}
    fun showError(){}

}