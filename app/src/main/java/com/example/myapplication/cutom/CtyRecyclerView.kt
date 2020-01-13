package com.example.myapplication.cutom

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.CommenRecylerViewAdapter
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.view.ZRecyclerView
import com.zgq.common.base.view.template.ZTemplateMoreView

class CtyRecyclerView : ZRecyclerView {

    companion object {
        const val PAGE_SIZE = 10
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    /**
     * 加载完成，设置有无更多数据
     * @param isLoadingData  true:有数据，显示加载样式  false:无数据，显示无数据样式
     */
    fun finishLoadMore(isLoadingData: Boolean) {
        this.isLoadingData = isLoadingData
        adapter()?.let {
            it.setStatus(if (isLoadingData) ZTemplateMoreView.LOAD else ZTemplateMoreView.EMPTY)
        }
    }

    /**
     * 加载成功，设置有无更多数据（传入获取到的数据个数、页数，自动判断底部的状态）
     * @param oldSize  加载更多数据之前的数据个数（刷新适配器使用到）
     * @param dataSize 本次加载更多获取到的数据个数（主要参数，用来判断是否加载更多，刷新适配器也会用到）
     * @param pageIndex 本次加载更多数据的页数
     */
    fun finishLoadMore(oldSize: Int, dataSize: Int, pageIndex: Int) {
        finishLoadMore(oldSize, dataSize, -1, pageIndex)
    }

    /**
     * 加载成功，设置有无更多数据（传入获取到的数据个数、页数，自动判断底部的状态）
     * @param oldSize  加载更多数据之前的数据个数（刷新适配器使用到）
     * @param dataSize 本次加载更多获取到的数据个数（主要参数，用来判断是否加载更多，刷新适配器也会用到）
     * @param slipSize 拼接数据的个数（刷新适配器会用到）
     * @param pageIndex 本次加载更多数据的页数
     */
    fun finishLoadMore(oldSize: Int, dataSize: Int, slipSize: Int, pageIndex: Int) {
        finishLoadMore(oldSize, dataSize, slipSize, pageIndex, PAGE_SIZE)
    }

    /**
     * 加载成功，设置有无更多数据（传入获取到的数据个数、页数，自动判断底部的状态）
     * @param oldSize   加载更多数据之前的数据个数（刷新适配器使用到）
     * @param dataSize  本次加载更多获取到的数据个数（主要参数，用来判断是否加载更多，刷新适配器也会用到）
     * @param slipSize  拼接数据的个数（刷新适配器会用到）
     * @param pageIndex 本次加载更多数据的页数
     * @param pageSize  每页加载的数据个数
     */
    fun finishLoadMore(oldSize: Int, dataSize: Int, slipSize: Int, pageIndex: Int, pageSize: Int) {
        if (pageIndex == 1 && dataSize <= 0) {
            // 第一页数据却数量<=0
            finishLoadHide()
        } else {
            // 非第一页数据或者数量>0
            this.isLoadingData = dataSize >= pageSize
        }
        adapter()?.let {
            // 修改底部加载更多样式
            it.setStatus(if (isLoadingData) ZTemplateMoreView.LOAD else ZTemplateMoreView.EMPTY)
            if (pageIndex == 1) {// 第一页，调用刷新的全部数据的方法
                this.scrollToPosition(0)
                it.notifyDataSetChanged()
                return
            }
            if (slipSize > 0) {
                // 拼接的数据数量大于0，说明加载更多的数据是自己处理添加到集合中的，刷新数据的数量为slipSize
                it.notifyItemRangeInserted(oldSize, slipSize)
            } else if (dataSize > 0) {
                // 拼接的数据数量小于0，调用插入多条数据的刷新方法，刷新数据的数量为dataSize
                it.notifyItemRangeInserted(oldSize, dataSize)
            }
        }
    }

    /**
     * 加载失败，调用此方法
     * 默认页面大于1，显示错误UI样式
     *
     */
    fun finishLoadError() {
        finishLoadError(2)
    }

    /**
     * 加载失败，调用此方法
     * pageIndex 页面 大于1显示错误UI样式  等于1 隐藏底部UI样式
     *
     */
    fun finishLoadError(pageIndex: Int) {
        if (pageIndex == 1) {
            finishLoadHide()
            return
        }
        this.isLoadingData = false
        adapter()?.let {
            // 修改底部加载更对的UI样式
            it.setStatus(ZTemplateMoreView.ERROR)
            it.getTemplateMoreView()?.let { footerView ->
                footerView.setOnClickListener { it1 ->
                    loadMoreListener?.let { loadMoreListener ->
                        // 显示加载样式
                        it.setStatus(ZTemplateMoreView.LOAD)
                        loadMoreListener.onCtyLoadErrorClick()
                        isLoadingData = false
                        footerView.setOnClickListener(null)
                    }
                }
            }
        }
    }

    /**
     * 隐藏底部布局，调用此方法
     * 默认 true：上拉不会加载更多
     */
    fun finishLoadHide() {
        finishLoadHide(true)
    }

    /**
     * 隐藏底部布局，调用此方法
     * @param showHide true：上拉不会加载更多  false:上拉可以继续加载更多
     */
    fun finishLoadHide(showHide: Boolean) {
        this.isLoadingData = !showHide
        adapter()?.let {
            it.setStatus(ZTemplateMoreView.HIDE)
        }
    }

    /**
     * 获取recycleview设置的adapter
     * @return
     */
    private fun adapter(): CommenRecylerViewAdapter? {
        adapter?.let {
            if (it is CommenRecylerViewAdapter) {
                return it
            }
        }
        return null
    }

}