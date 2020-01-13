package com.example.myapplication.mvp.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.adapter.CommenRecylerViewAdapter
import com.example.myapplication.mvp.presenter.RecyclerViewPresenter
import com.zgq.common.base.mvp.ZBaseActivity
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.view.ZRecyclerView
import kotlinx.android.synthetic.main.activity_recyclerview_layout.*

class RecyclerViewActivity: ZBaseActivity<RecyclerViewPresenter>(), ZRecyclerView.OnZLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    override val contentView: Int
        get() = R.layout.activity_recyclerview_layout

    override val getPresenter: RecyclerViewPresenter
        get() = RecyclerViewPresenter(this)

    lateinit var mAdapter: CommenRecylerViewAdapter
    var mListData = ArrayList<Any>()
    var pageIndex: Int = 1

    override fun initView() {
        mRecyclerView.setOnZLoadMoreListener(this)
        swipeRefresh.setOnRefreshListener(this)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = CommenRecylerViewAdapter(this, mListData, true)
        mRecyclerView.adapter = mAdapter
        loadGoodsList()
    }

    fun loadGoodsList(){
        showLoading()
        mPresenter?.loadGoodsList()
    }

    fun onSuccess(oldSize: Int, dataSize: Int){
        swipeRefresh.hindRefreshing()
        if(pageIndex == 1 && dataSize <= 0){
            showEmpty()
        }else{
            showData()
        }
        ZLog.e("数量 = $dataSize")
        // 刷新加载更多样式
        mRecyclerView.finishLoadMore(oldSize, dataSize, pageIndex)

    }

    fun onError(){
        swipeRefresh.hindRefreshing()
        if(pageIndex == 1){
            showError()
        }
        // 刷新加载更多样式
        mRecyclerView.finishLoadError(pageIndex)
    }

    override fun showLoading() {

    }

    override fun showData() {

    }

    override fun showEmpty() {

    }

    override fun showError() {

    }

    override fun onCtyLoadMore() {
        pageIndex++
        loadGoodsList()
    }

    override fun onCtyLoadErrorClick() {
        loadGoodsList()
    }

    override fun onRefresh() {
        pageIndex = 1
        loadGoodsList()
    }

}