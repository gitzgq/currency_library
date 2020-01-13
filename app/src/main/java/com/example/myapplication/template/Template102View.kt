package com.example.myapplication.template

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import com.example.myapplication.bean.Template102Bean
import com.zgq.common.base.glide.ZGlide
import com.zgq.common.base.view.template.ZBaseRelativeTemplateView
import kotlinx.android.synthetic.main.template_102_view_layout.view.*

class Template102View : ZBaseRelativeTemplateView {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun initView() {

    }

    override fun setData(any: Any?, position: Int, bundle: Bundle?) {
        if(null == mContext || null == any || position < 0 || any !is Template102Bean){
            return
        }
        ZGlide.instence.loadRoundImg(mContext, m102IvImg, any.mainPicUrl)

        m102TvTitle.text = any.title

        m102TvPrice.text = "￥${any.salePrice}"

    }

    override fun onDetached() {

    }

}