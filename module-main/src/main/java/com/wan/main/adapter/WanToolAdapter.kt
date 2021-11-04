package com.wan.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wan.main.R
import com.wan.main.entity.ToolEntity

/**
 * Description:
 * @author lilu0916 on 2021/11/3
 *  No one knows this better than me
 */
class WanToolAdapter(data:MutableList<ToolEntity.ToolChild>) :BaseQuickAdapter<ToolEntity.ToolChild,BaseViewHolder>(R.layout.item_tool,data) {

    override fun getItemCount(): Int {
        data?.let {

            if(data.size > 7){
                return 8
            }
        }
        return super.getItemCount()
    }
    override fun convert(holder: BaseViewHolder, item: ToolEntity.ToolChild) {
        holder?.let {

            if(it.layoutPosition == 7){
                it.setText(R.id.tv_tool_name,"更多")
            }else{
                it.setText(R.id.tv_tool_name,item.name)
            }
        }
    }
}