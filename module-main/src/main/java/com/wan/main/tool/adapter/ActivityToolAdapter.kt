package com.wan.main.tool.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wan.main.R
import com.wan.main.entity.ToolEntity

/**
 * Description:
 * 常用工具页 - 适配器
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class ActivityToolAdapter(data:MutableList<ToolEntity.ToolChild>) : BaseQuickAdapter<ToolEntity.ToolChild,BaseViewHolder>(R.layout.item_ac_tool,data) {


    override fun convert(holder: BaseViewHolder, item: ToolEntity.ToolChild) {
        //常用网站名字
        holder.setText(R.id.tv_item_tool_title,item.name)
        //常用网站标签
        holder.setText(R.id.item_tool_category_tv,item.category)
        holder.setText(R.id.tv_item_tool_link,item.link)
        if(holder.layoutPosition % 2 ==0){
            holder.setBackgroundColor(R.id.item_tool_ctl,ContextCompat.getColor(context,R.color.color_FFFEEE))
        }else{
            holder.setBackgroundColor(R.id.item_tool_ctl,ContextCompat.getColor(context,R.color.color_FFFFFF))
        }

    }
}