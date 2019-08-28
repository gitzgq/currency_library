package com.zgq.common.base

import com.zgq.common.base.bean.HomepageBannerBean
import com.zgq.common.base.mvp.ZBasePresenter
import com.zgq.common.base.mvp.ZBaseView

class MainPresenter(view : ZBaseView) : ZBasePresenter<MainActivity>(view){

    private val view : MainActivity? = view as? MainActivity?: null
    private val model : MainModel = MainModel(this)

    /**
     * 获取banner数据
     */
    fun loadBanner(){
        model?.loadBanner()
    }


    /**
     * banner数据获取成功
     */
    fun onSuccess(data: ArrayList<HomepageBannerBean>?){
        view?.onSuccess(data)
    }

    override fun clear() {
        model?.clearNetWork()
        super.clear()
    }
}