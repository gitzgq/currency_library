package com.example.myapplication.template

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import com.example.myapplication.bean.Template101Bean
import com.zgq.common.base.glide.ZGlide
import com.zgq.common.base.other.ZScreenUI
import com.zgq.common.base.view.template.ZBaseRelativeTemplateView
import kotlinx.android.synthetic.main.template_101_view_layout.view.*

class Template101View : ZBaseRelativeTemplateView {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun initView() {
        mContext?.let {
            val w = ZScreenUI.width(it) - ZScreenUI.dpTopx(it, 30)
            ZScreenUI.setViewWH(m101IvImg, w, w * 159 / 329)
        }

    }

    override fun setData(any: Any?, position: Int, bundle: Bundle?) {
        if(null == mContext || null == any || position < 0 || any !is Template101Bean){
            return
        }
        mContext?.let {
            ZGlide.instence.loadRoundImg(it, m101IvImg, any.image)
        }

        m101TvContent?.text = any?.linkTitle?: "===$position"
    }

    override fun onDetached() {

    }

}