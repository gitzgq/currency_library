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

    private var mTouchSlop: Int = 0
    // 上一次触摸时的X坐标
    private var mPrevX: Float = 0.toFloat()

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
        // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(mContext).scaledTouchSlop
        setRefreshColor(R.color.refresh_color)
    }

    @SuppressLint("ResourceAsColor")
    fun setRefreshColor(color: Int) {
        this.setColorSchemeColors(color)
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

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            // 按下
            MotionEvent.ACTION_DOWN -> mPrevX = event.x
            // 移动
            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = abs(eventX - mPrevX)
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 60) {
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

}
