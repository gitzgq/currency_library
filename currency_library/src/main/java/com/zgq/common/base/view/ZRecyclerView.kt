package com.zgq.common.base.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * 加载更多
 */
class ZRecyclerView : RecyclerView {

    // 默认使用加载更多，当获取到第一页数据时，进行判断是否要加载更多
    private var isLoadingData = false

    /**
     * 滑动监听
     */
    internal var onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
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

    private var loadMoreListener: OnZLoadMoreListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
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

    /**
     * 加载成功，设置有无更多数据
     *
     * @param isLoadingData true:有数据，显示加载样式  false:无数据，显示无数据样式
     * @param refreshFooter true:刷新底部样式  false:不刷新底部样式
     */
    @JvmOverloads
    fun finishLoadMore(isLoadingData: Boolean = true, refreshFooter: Boolean = false) {
        this.isLoadingData = isLoadingData
    }

    /**
     * 加载成功，设置有无更多数据（传入获取到的数据个数、页数，自动判断底部的状态）
     *
     * @param oldSize   加载更多数据之前的数据个数（刷新适配器使用到）
     * @param dataSize  本次加载更多获取到的数据个数（主要参数，用来判断是否加载更多，刷新适配器也会用到）
     * @param pageIndex 本次加载更多数据的页数
     */
    fun finishLoadMore(oldSize: Int, dataSize: Int, pageIndex: Int) {
        finishLoadMore(oldSize, dataSize, -1, pageIndex)
    }

    @JvmOverloads
    fun finishLoadMore(oldSize: Int, dataSize: Int, slipSize: Int, pageIndex: Int, pageSize: Int = 10) {
    }

    /**
     * 加载失败，调用此方法
     */
    fun finishLoadError() {}

    /**
     * 隐藏底部布局，调用此方法
     *
     * @param showHide true：上拉不会加载更多  false:上拉可以继续加载更多
     */
    @JvmOverloads
    fun finishLoadHide(showHide: Boolean = true) {
    }


}
/**
 * 加载成功，设置有无更多数据（默认为true有更多数据）
 * true:有数据，显示加载样式
 */
/**
 * 加载成功，设置有无更多数据
 *
 * @param isLoadingData true:有数据，显示加载样式  false:无数据，显示无数据样式
 */
/**
 * 加载成功，设置有无更多数据（传入获取到的数据个数、页数，自动判断底部的状态）
 *
 * @param oldSize   加载更多数据之前的数据个数（刷新适配器使用到）
 * @param dataSize  本次加载更多获取到的数据个数（主要参数，用来判断是否加载更多，刷新适配器也会用到）
 * @param slipSize  拼接数据的个数（刷新适配器会用到）
 * @param pageIndex 本次加载更多数据的页数
 */
/**
 * 隐藏底部布局，调用此方法
 * 默认 true：上拉不会加载更多
 */
