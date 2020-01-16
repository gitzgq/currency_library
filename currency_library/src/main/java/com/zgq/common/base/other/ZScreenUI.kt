package com.zgq.common.base.other

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.zgq.common.base.app.ZBaseApplication

object ZScreenUI {

    /** 获取屏幕得宽 */
    fun width(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /** 获取屏幕得高 */
    fun height(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * dp转px
     * @param dp
     */
    fun dpTopx(context: Context, dp: Int): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

    /**
     * 设置view的宽
     */
    fun setViewW(view: View, w: Int) {
        setViewWH(view, w, -1)
    }

    /**
     * 设置view的高
     */
    fun setViewH(view: View, h: Int) {
        setViewWH(view, -1, h)
    }

    /**
     * 设置view的宽高
     */
    fun setViewWH(view: View, w: Int, h: Int) {
        val eParams = view.layoutParams
        when (eParams) {
            is RelativeLayout.LayoutParams,
            is LinearLayout.LayoutParams,
            is FrameLayout.LayoutParams,
            eParams -> {
                if (w > 0) {
                    eParams.width = w
                }
                if (h > 0) {
                    eParams.height = h
                }
            }
        }
        view.layoutParams = eParams
    }

    /**
     * 设置view的上下左右的边距
     * @param view
     * @param left
     * @param top
     * @param rigth
     * @param bottom
     */
    fun setMargin(view: View, left: Int, top: Int, rigth: Int, bottom: Int) {
        if (left < 0 || top < 0 || rigth < 0 || bottom < 0) {
            return
        }
        val eParams = view.layoutParams
        when (eParams) {
            is RelativeLayout.LayoutParams -> eParams.setMargins(left, top, rigth, bottom)
            is LinearLayout.LayoutParams -> eParams.setMargins(left, top, rigth, bottom)
            is FrameLayout.LayoutParams -> eParams.setMargins(left, top, rigth, bottom)
        }
        view.layoutParams = eParams
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    fun getStatusHeight(context: Context): Int {
        var result = 90
            val res = context.resources
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        return result
    }

}
