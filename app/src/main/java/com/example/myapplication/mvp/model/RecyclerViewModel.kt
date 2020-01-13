package com.example.myapplication.mvp.model

import com.example.myapplication.bean.GoodsListBean
import com.example.myapplication.mvp.presenter.RecyclerViewPresenter
import com.example.myapplication.net.request.RequestUtil
import com.example.myapplication.net.response.BaseObserver
import com.zgq.common.base.base_mvp.ZBaseModel
import com.zgq.common.base.base_mvp.ZBasePresenter
import io.reactivex.disposables.Disposable

class RecyclerViewModel(basePresenter: ZBasePresenter<*>): ZBaseModel(basePresenter) {

    val presenter: RecyclerViewPresenter = basePresenter as RecyclerViewPresenter

    /**
     * 商品列表
     */
    fun loadGoodsList(pageIndex: Int, list: ArrayList<Any>){
        RequestUtil.loadGoodsList(pageIndex, object : BaseObserver<GoodsListBean>() {
            override fun onDisposable(d: Disposable) {
                disposable.add(d)
            }

            override fun onSuccess(t: GoodsListBean, code: Int) {
                if(pageIndex == 1){
                    list.clear()
                }
                var dataSize = 0
                var oldSize = list.size
                t.list?.let { it ->
                    if(it.isNotEmpty()){
                        dataSize = it.size
                        list.addAll(it)
                    }
                }
                presenter.onSuccess(oldSize, dataSize)
            }

            override fun onError(code: Int, msg: String?) {
                presenter.onError()
            }

        })
    }

}