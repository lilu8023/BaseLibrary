package com.wan.main.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lilu.apptool.utils.StringUtils
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
            it.article = item
            if(StringUtils.isEmpty(item.desc)){
                it.itemWanDesTv.visibility = View.GONE
            }else{
                it.itemWanDesTv.visibility = View.VISIBLE
                it.itemWanDesTv.text = StringUtils.getHtmlString(item.desc)
            }

        }
    }


}