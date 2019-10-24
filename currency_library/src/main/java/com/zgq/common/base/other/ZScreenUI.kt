package com.zgq.common.base.other

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

object ZScreenUI{

    fun width(context : Context?) : Int{
        context?.let {
            val dm = it?.resources?.displayMetrics
            return dm?.widthPixels?: 1080
        }
        return 1080
    }

    fun height(context : Context?) : Int{
        context?.let {
            val dm = it?.resources?.displayMetrics
            return dm?.heightPixels?: 1920
        }
        return 1920
    }

    /**
     * dp转px
     * @param dp
     */
    fun dpTopx(context : Context?, dp: Int): Int {
        context?.let {
            val scale = it?.resources?.displayMetrics?.density?: 3.0f
            return (dp * scale).toInt()
        }
        return (3.0f * dp).toInt()
    }

    /**
     * 设置view的宽
     */
    fun setViewW(view: View?, w: Int) {
        setViewWH(view, w, -1)
    }

    /**
     * 设置view的高
     */
    fun setViewH(view: View?, h: Int) {
        setViewWH(view, -1, h)
    }

    /**
     * 设置view的宽高
     */
    fun setViewWH(view: View?, w: Int, h: Int) {
        view?.let {
            val eParams = view?.layoutParams
            eParams?.let {it1 ->
                if (it1 is RelativeLayout.LayoutParams || it1 is LinearLayout.LayoutParams || it1 is FrameLayout.LayoutParams
                        || it1 is ViewGroup.LayoutParams) {
                    if (w > 0) {
                        it1?.width = w
                    }
                    if (h > 0) {
                        it1?.height = h
                    }
                }
                it?.layoutParams = it1
            }
        }
    }

    /**
     * 设置view的上下左右的边距
     * @param view
     * @param left
     * @param top
     * @param rigth
     * @param bottom
     */
    fun setMargin(view: View?, left: Int, top: Int, rigth: Int, bottom: Int) {
        if (left < 0 || top < 0 || rigth < 0 || bottom < 0) {
            return
        }
        view?.let {
            val eParams = it?.layoutParams
            eParams?.let { it1 ->
                when (it1) {
                    is RelativeLayout.LayoutParams -> it1?.setMargins(left, top, rigth, bottom)
                    is LinearLayout.LayoutParams -> it1?.setMargins(left, top, rigth, bottom)
                    is FrameLayout.LayoutParams -> it1?.setMargins(left, top, rigth, bottom)
                }
                it?.layoutParams = it1
            }
        }
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    fun getStatusHeight(context: Context?): Int {
        var result = 0
        context?.let {
            val res = it.resources
            val resourceId = it.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

}
