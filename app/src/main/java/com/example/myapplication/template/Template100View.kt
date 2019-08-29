package com.example.myapplication.template

import android.content.Context
import android.util.AttributeSet
import com.zgq.common.base.view.template.BaseRelativeTemplateView
import kotlinx.android.synthetic.main.template_100_view_layout.view.*

class Template100View : BaseRelativeTemplateView {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun initView() {
        m_100_tv_content?.text = "这是模板"
    }

    override fun onDetached() {

    }

}