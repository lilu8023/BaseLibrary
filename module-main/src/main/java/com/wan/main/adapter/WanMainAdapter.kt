package com.wan.main.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.main.R
import com.wan.main.databinding.ItemWanBinding
import com.wan.main.entity.WanMainEntity

/**
 * Description:
 * @author lilu0916 on 2021/6/16
 *  No one knows this better than me
 */
class WanMainAdapter(data:MutableList<WanMainEntity.WanMainChild>) : BaseQuickAdapter<WanMainEntity.WanMainChild,BaseDataBindingHolder<ItemWanBinding>>(R.layout.item_wan,data) {

    override fun convert(holder: BaseDataBindingHolder<ItemWanBinding>, item: WanMainEntity.WanMainChild) {
        holder.dataBinding?.let {
            it.tvItemWan.apply {
                setTextColor(ContextCompat.getColor(context,R.color.color_1EAD36))
                text = item.title
            }
        }
    }


}