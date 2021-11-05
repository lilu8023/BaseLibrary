package com.wan.system.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.wan.system.R
import com.wan.system.databinding.ItemSystemBinding
import com.wan.system.entity.SystemEntity

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SystemChildAdapter(data:MutableList<SystemEntity>):BaseQuickAdapter<SystemEntity,BaseDataBindingHolder<ItemSystemBinding>>(R.layout.item_system,data) {


    override fun convert(holder: BaseDataBindingHolder<ItemSystemBinding>, item: SystemEntity) {

        holder.dataBinding?.fatherInfo = item
    }
}