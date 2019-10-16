package com.zgq.common.base.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView (自定义加载更多)
 */
open class ZRecyclerView : RecyclerView {

    // 默认使用加载更多，当获取到第一页数据时，进行判断是否要加载更多
    var isLoadingData = false

    /**
     * 滑动监听
     */
    private val onScrollListener: OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isLoadingData) {
                return
            }
            if (!recyclerView.canScrollVertically(1)) {// 滑动到底部
                if (null != loadMoreListener && isLoadingData) {
                    loadMoreListener?.onCtyLoadMore()
                    // 设置不可加载状态
                    isLoadingData = false
                }
            }
        }
    }

    var loadMoreListener: OnZLoadMoreListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        addOnScrollListener(onScrollListener)
    }

    /**
     * 定义加载更多、底部加载失败点击 回调接口
     */
    interface OnZLoadMoreListener {
        fun onCtyLoadMore()
        fun onCtyLoadErrorClick()
    }

    fun setOnZLoadMoreListener(listener: OnZLoadMoreListener) {
        loadMoreListener = listener
    }

}
