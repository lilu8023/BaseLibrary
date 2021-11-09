package com.wan.system.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wan.system.R
import com.wan.system.entity.HierarchyEntity

/**
 * Description:
 * @author lilu0916 on 2021/11/9
 *  No one knows this better than me
 */
class SystemItemAdapter(data:MutableList<HierarchyEntity.HierarchyChild>) :
        BaseQuickAdapter<HierarchyEntity.HierarchyChild,BaseViewHolder>(R.layout.item_hierarchy,data) {


    override fun convert(holder: BaseViewHolder, item: HierarchyEntity.HierarchyChild) {
        holder.setText(R.id.item_hierarchy_title_tv,item.title)
    }
}