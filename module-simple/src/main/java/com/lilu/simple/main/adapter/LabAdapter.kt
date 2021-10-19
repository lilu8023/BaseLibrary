package com.lilu.simple.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lilu.simple.R

/**
 * Description:
 * @author lilu0916 on 2021/6/11 10:48
 *  No one knows this better than me
 */
class LabAdapter() : RecyclerView.Adapter<LabAdapter.LabViewHolder>(){

    private lateinit var mList: MutableList<String>

    constructor(list: MutableList<String>) : this(){
        this.mList = list
    }


    inner class LabViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        val tvItemSimple:TextView = itemView.findViewById(R.id.tv_item_simple)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_simple,parent,false)
        var lp:ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        itemView.layoutParams = lp

        return LabViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LabViewHolder, position: Int) {
        holder.tvItemSimple.text = mList[position]
    }

    override fun getItemCount(): Int {
        return mList?.size
    }
}