package com.example.myapplication.mvp.presenter

import com.example.myapplication.mvp.model.RecyclerViewModel
import com.example.myapplication.mvp.view.RecyclerViewActivity
import com.zgq.common.base.mvp.ZBasePresenter
import com.zgq.common.base.mvp.ZBaseView

class RecyclerViewPresenter(view: ZBaseView): ZBasePresenter<RecyclerViewActivity>(view) {

    private val view : RecyclerViewActivity = view as RecyclerViewActivity
    private val model : RecyclerViewModel = RecyclerViewModel(this)

    fun loadGoodsList(){
        view?.let {
            model?.loadGoodsList(it.pageIndex, it.mListData)
        }
    }

    fun onSuccess(oldSize: Int, dataSize: Int){
        view?.onSuccess(oldSize, dataSize)
    }


    fun onError(){
        view?.onError()
    }

    override fun clear() {
        model?.clearNetWork()
        super.clear()
    }

}