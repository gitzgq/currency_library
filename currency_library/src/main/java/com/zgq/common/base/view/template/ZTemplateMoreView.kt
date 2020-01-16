package com.zgq.common.base.view.template

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zgq.common.base.other.ZLog
import kotlinx.android.synthetic.main.template_more_view_layout.view.*
import java.util.*

class ZTemplateMoreView : ZBaseRelativeTemplateView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        /** 隐藏 */
        const val HIDE = 1
        /** 加载中 */
        const val LOAD = 2
        /** 没有更多数据 */
        const val EMPTY = 3
        /** 加载失败 */
        const val ERROR = 4
    }

    var timer: Timer? = null

    override fun initView() {

    }

    fun setStatus(status: Int) {
        when (status) {
            HIDE -> {
                zLayoutMore?.visibility = View.GONE
            }
            LOAD -> {
                zLayoutMore?.visibility = View.VISIBLE
                zProgress?.visibility = View.VISIBLE
                zTvContent?.visibility = View.VISIBLE
                zTvContent?.text = "正在加载中..."
            }
            EMPTY -> {
                zLayoutMore?.visibility = View.VISIBLE
                zProgress?.visibility = View.GONE
                zTvContent?.visibility = View.GONE
                cancel()
                timer = Timer()
                timer?.schedule(object : TimerTask() {// 延迟100毫秒 显示没有更多数据
                    override fun run() {
                        (mContext as AppCompatActivity).runOnUiThread {
                            cancel()
                            zTvContent?.visibility = View.VISIBLE
                            zTvContent?.text = "没有更多数据了"
                        }
                    }
                }, 100)

            }
            ERROR -> {
                zLayoutMore?.visibility = View.VISIBLE
                zProgress?.visibility = View.GONE
                zTvContent?.visibility = View.VISIBLE
                zTvContent?.text = "网络不稳定，点击重试"
            }
        }
    }

    private fun cancel() {
        timer?.let {
            it.purge()
            it.cancel()
        }
    }

    override fun onDetached() {
        cancel()
    }

}