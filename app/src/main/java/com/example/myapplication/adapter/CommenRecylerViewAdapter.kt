package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.template.TemplateUtil
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.view.template.ZBaseTemplateI
import com.zgq.common.base.view.template.ZTemplateMoreView

class CommenRecylerViewAdapter() : RecyclerView.Adapter<CommenRecylerViewAdapter.MyHolder>(){

    private var contentView : View? = null

    private var context : Context? = null
    private var datas = ArrayList<Any>()
    private var more : Boolean = false
    private lateinit var inflater: LayoutInflater
    private lateinit var templateMoreView : ZTemplateMoreView

    constructor(context : Context, datas : ArrayList<Any>) : this() {
        this.context = context
        this.datas = datas
        this.inflater = LayoutInflater.from(context)
    }

    constructor(context : Context, datas : ArrayList<Any>, more : Boolean) : this() {
        this.context = context
        this.datas = datas
        this.more = more
        this.inflater = LayoutInflater.from(context)
        templateMoreView = this.inflater.inflate(R.layout.template_more_view_layout, null) as ZTemplateMoreView
    }

    override fun getItemCount(): Int {
        if(more){
            return datas.size + 1
        }
        return datas.size
    }

    override fun getItemViewType(position: Int): Int {
        if(null == datas || datas.isEmpty()){
            return 0
        }
        if(position > 0 && position == datas.size && more){// 返回加载更多的模板id
            ZLog.e("加载更多 $position")
            return TemplateUtil.T_100
        }
        return TemplateUtil.instence.getTemplateId(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        ZLog.e("viewType = $viewType")
        contentView = if(more && viewType == TemplateUtil.T_100){
            templateMoreView
        }else{
            TemplateUtil.instence.getContentView(this.context, viewType, inflater)
        }
        return MyHolder(contentView?: View(this.context))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if(null == holder?.itemView){
            return
        }
        if(holder?.itemView is ZTemplateMoreView){// 加载更多不往下执行
            return
        }
        if(position >= datas?.size){
            return
        }
        val data : Any = datas?.get(position)
        // 刷新item数据
        if(holder?.itemView is ZBaseTemplateI){
            holder?.itemView.setData(data, position, null)
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // TODO ---------------------------------------------------- 加载更多相关 ----------------------------------------------------

    /**
     * 设置底部加载更多的状态
     * @param status
     */
    fun setStatus(status: Int) {
        templateMoreView?.let {
            it.setStatus(status)
//            if(datas.isNotEmpty()){
//                notifyItemChanged(datas.size + 1)
//            }
        }
    }

    fun getTemplateMoreView(): ZTemplateMoreView? {
        return templateMoreView
    }

    override fun onViewAttachedToWindow(holder: MyHolder) {
        super.onViewAttachedToWindow(holder)
        if (!more) {// 不添加更多时，不执行一下操作
            return
        }
        if (holder?.itemView != null && holder.itemView is ZTemplateMoreView) {
            val layoutParams = holder.itemView.layoutParams
            if (null != layoutParams && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                layoutParams.isFullSpan = true
            }

        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (!more) {// 不添加更多时，不执行一下操作
            return
        }
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (null != datas && datas.size > 0 && position == datas.size) {
                        manager.spanCount
                    } else 1
                }
            }
        }
    }

}