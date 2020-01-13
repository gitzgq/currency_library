package com.example.myapplication.mvp.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.adapter.CommenRecylerViewAdapter
import com.example.myapplication.mvp.presenter.RecyclerViewPresenter
import com.zgq.common.base.base_mvp.ZBaseActivity
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.view.ZRecyclerView
import kotlinx.android.synthetic.main.activity_recyclerview_layout.*

/**
 * 下拉刷新、加载更多
 */
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

    /** 请求-商品列表 */
    fun loadGoodsList(){
        showLoading()
        mPresenter?.loadGoodsList()
    }

    /** 列表数据获取成功 */
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

    /** 列表数据获取失败 */
    fun onError(){
        swipeRefresh.hindRefreshing()
        if(pageIndex == 1){
            showError()
        }
        // 刷新加载更多样式
        mRecyclerView.finishLoadError(pageIndex)
    }

    // 显示loading布局
    override fun showLoading() {

    }

    // 显示列表布局
    override fun showData() {

    }

    // 显示无数据布局
    override fun showEmpty() {

    }

    // 显示错误布局
    override fun showError() {

    }

    /** 加载更多回调 */
    override fun onCtyLoadMore() {
        pageIndex++
        loadGoodsList()
    }

    /** 加载更多时异常，点击重试回调 */
    override fun onCtyLoadErrorClick() {
        loadGoodsList()
    }

    /** 下拉刷新 */
    override fun onRefresh() {
        pageIndex = 1
        loadGoodsList()
    }

}