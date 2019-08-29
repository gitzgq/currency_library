package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.template.TemplateUtil

class CommenRecylerViewAdapter : RecyclerView.Adapter<CommenRecylerViewAdapter.MyHolder>(){

    private var contentView : View? = null

    private var context : Context? = null
    private var datas : List<Any>? = null
    private var more : Boolean = false
    private var inflater: LayoutInflater? = null

    constructor(context : Context, datas : List<Any>, more : Boolean){
        this.context = context
        this.datas = datas
        this.more = more
        inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return datas?.size?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return TemplateUtil.instence.getTemplateId(datas?.get(position)?: null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        contentView = TemplateUtil.instence.getContentView(this.context, viewType )

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}