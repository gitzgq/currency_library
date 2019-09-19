package com.zgq.common.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ZViewPager : ViewPager {

    // true：正常滑动    false：不可滑动
    private var scrollable = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    /**
     * 设置是否可以左右滑动
     * @param scrollable    true：可以  fasle：不可以
     */
    fun setScrollable(scrollable : Boolean) {
        this.scrollable = scrollable
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(scrollable){
            return super.onInterceptTouchEvent(ev)
        }
        return false
    }
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if(scrollable){
            return super.onTouchEvent(ev)
        }
        return false
    }

}