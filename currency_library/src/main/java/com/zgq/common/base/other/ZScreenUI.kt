package com.zgq.common.base.other

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

object ZScreenUI{

    fun width(context : Context) : Int{
        if(null == context){
            return 1080
        }
        val dm = context?.resources?.displayMetrics
        return dm?.widthPixels?: 1080
    }

    fun height(context : Context) : Int{
        if(null == context){
            return 1920
        }
        val dm = context?.resources?.displayMetrics
        return dm?.heightPixels?: 1920
    }

    /**
     * dp转px
     * @param dp
     */
    fun dpTopx(context : Context, dp: Int): Int {
        val scale = context?.resources?.displayMetrics?.density?: 3.0f
        return (dp * scale).toInt()
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
    fun setViewWH(view: View?, w: Int, h: Int) {
        if (null == view) {
            return
        }
        val eParams = view?.layoutParams
        if (eParams is RelativeLayout.LayoutParams || eParams is LinearLayout.LayoutParams || eParams is FrameLayout.LayoutParams
                || eParams is ViewGroup.LayoutParams) {
            if (w > 0) {
                eParams?.width = w
            }
            if (h > 0) {
                eParams?.height = h
            }
        }
        if (null != eParams) {
            view?.layoutParams = eParams
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
        if (null == view || left < 0 || top < 0 || rigth < 0 || bottom < 0) {
            return
        }
        val eParams = view?.layoutParams
        when (eParams) {
            is RelativeLayout.LayoutParams -> eParams?.setMargins(left, top, rigth, bottom)
            is LinearLayout.LayoutParams -> eParams?.setMargins(left, top, rigth, bottom)
            is FrameLayout.LayoutParams -> eParams?.setMargins(left, top, rigth, bottom)
        }
        if(null != eParams){
            view?.layoutParams = eParams
        }

    }

}
