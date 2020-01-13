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

    private lateinit var contentView : View

    private lateinit var context : Context
    private lateinit var datas : ArrayList<Any>
    private lateinit var inflater: LayoutInflater
    /** 加载更多布局View */
    private lateinit var templateMoreView : ZTemplateMoreView
    /** 是否显示加载更多布局 true：显示  false：不显示  默认为false不显示 */
    private var more : Boolean = false

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
        if(datas.isEmpty()){
            return 0
        }
        if(position > 0 && position == datas.size && more){// 返回加载更多的模板id
            return TemplateUtil.T_100
        }
        return TemplateUtil.instence.getTemplateId(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        when(viewType){
            // 加载更多模板id
            TemplateUtil.T_100 ->{
                if(more){
                    contentView = templateMoreView
                }
            }
            // 其他模板id
            else ->{
                contentView = TemplateUtil.instence.getContentView(this.context, viewType, inflater)
            }
        }
        return MyHolder(contentView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView?.let {
            if(it is ZTemplateMoreView){// 加载更多不往下执行
                return
            }
            if(position >= datas.size){
                return
            }
            // 刷新item数据
            if(it is ZBaseTemplateI){
                it.setData(datas[position], position, null)
            }
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
        holder.itemView?.let {
            if(it is ZTemplateMoreView){
                it.layoutParams?.let { layoutParams ->
                    if(layoutParams is StaggeredGridLayoutManager.LayoutParams){
                        layoutParams.isFullSpan = true
                    }
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (!more) {// 不添加更多时，不执行一下操作
            return
        }
        recyclerView.layoutManager?.let {
            if(it is GridLayoutManager){
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val size: Int = datas.size
                        return if (size > 0 && position == size) {
                            it.spanCount
                        } else 1
                    }
                }
            }
        }
    }

}