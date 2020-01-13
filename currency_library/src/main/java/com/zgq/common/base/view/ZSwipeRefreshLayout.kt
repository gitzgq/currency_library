package com.zgq.common.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zgq.common.base.R
import kotlin.math.abs

/**
 * 下拉刷新
 */
class ZSwipeRefreshLayout : SwipeRefreshLayout {

    private var mContext: Context? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
        init()
    }


    private fun init() {
        setRefreshColor(R.color.refresh_color)
    }

    @SuppressLint("ResourceAsColor")
    fun setRefreshColor(color: Int) {
        this.setColorSchemeResources(color)
    }

    /**
     * 隐藏加载进度条
     */
    fun hindRefreshing() {
        if (isRefreshing) {
            isRefreshing = false
        }
    }

    /**
     * 设置是否可以刷新
     * @param enabled  true: 可以刷新   false：不可以刷新
     */
    fun setCtyEnabled(enabled: Boolean) {
        isEnabled = enabled
    }

}
