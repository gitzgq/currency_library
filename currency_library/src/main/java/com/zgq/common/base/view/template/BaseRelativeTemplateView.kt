package com.zgq.common.base.view.template

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment

open class BaseRelativeTemplateView : RelativeLayout, BaseTemplateI{
    /** 上下文  */
    var mContext: Context

    constructor(context: Context): super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        mContext = context
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(isInEditMode){
            return
        }
        initView()
    }

    override fun onDetachedFromWindow() {
        onDetached()
        super.onDetachedFromWindow()
    }

    /** 初始化view、数据 */
    open fun initView(){}

    /** 销毁 */
    open fun onDetached(){}

    override fun setData(bundle: Bundle) {
    }

    override fun setData(any: Any) {
    }

    override fun setData(any: Any, position: Int) {
    }

    override fun setData(any: Any, bundle: Bundle) {
    }

    override fun setData(any: Any, position: Int, bundle: Bundle) {
    }

    override fun setData(bundle: Bundle, dialog: DialogFragment, dialogClickListener: OnDialogClickListener) {
    }

}