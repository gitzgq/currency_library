package com.zgq.common.base.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.zgq.common.base.R

class ZDelEditText : AppCompatEditText, View.OnFocusChangeListener, TextWatcher {

    // 删除按钮
    private var delDrawable : Drawable? = null
    // 是否有焦点
    private var hasFoucs : Boolean = false

    constructor(context : Context) : super(context)

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs)

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr)

    init {
        delDrawable = compoundDrawables?.get(2)

        if(null == delDrawable){
            delDrawable = resources.getDrawable(R.mipmap.img_edit_del_icon)
        }
        delDrawable?.setBounds(0, 0, delDrawable?.intrinsicWidth?: 0, delDrawable?.intrinsicHeight?: 0)
        addTextChangedListener(this)
        onFocusChangeListener = this
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible true：显示  false：隐藏
     */
    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) delDrawable else null
        setCompoundDrawables(compoundDrawables?.get(0), compoundDrawables?.get(1), right, compoundDrawables?.get(3))
    }

    // 焦点监听
    override fun onFocusChange(p0: View?, p1: Boolean) {
        this.hasFoucs = p1
        if(p1){
            setClearIconVisible(text?.length?: 0 > 0)
        }else{
            setClearIconVisible(p1)
        }

    }

    // 输入监听
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(hasFoucs){
            setClearIconVisible(text?.length?: 0 > 0)
        }
        textWatcherCallBack?.onTextChanged(p0, p1, p2, p3)
    }

    override fun afterTextChanged(p0: Editable?) {
        textWatcherCallBack?.afterTextChanged(p0)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        textWatcherCallBack?.beforeTextChanged(p0, p1, p2, p3)
    }

    // 删除按钮的点击事件
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight
                if (touchable) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    // 暴露给外部的回调接口
    interface OnTextWatcherCallBack{
        fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)

        fun afterTextChanged(p0: Editable?)

        fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
    }

    var textWatcherCallBack : OnTextWatcherCallBack? = null

    fun textWatcherCallBack(textWatcherCallBack : OnTextWatcherCallBack){
        this.textWatcherCallBack = textWatcherCallBack
    }

}