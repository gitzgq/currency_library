package com.zgq.common.base.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

class ZNestedScrollView : NestedScrollView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

}