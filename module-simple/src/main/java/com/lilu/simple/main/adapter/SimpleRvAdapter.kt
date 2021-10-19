package com.lilu.simple.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lilu.simple.R

/**
 * Description:
 * @author lilu0916 on 2021/6/10 15:11
 *  No one knows this better than me
 */
class SimpleRvAdapter(data:MutableList<String>) : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_simple,data) {


    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_item_simple,item)
    }
}