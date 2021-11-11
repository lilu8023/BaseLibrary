package com.wan.solution.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lilu.apptool.utils.StringUtils
import com.wan.solution.R
import com.wan.solution.databinding.ItemSolutionBinding
import com.wan.solution.entity.SolutionEntity

/**
 * Description:
 * 首页 - 问答适配器
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SolutionAdapter(data:MutableList<SolutionEntity.SolutionChild>) : BaseQuickAdapter<SolutionEntity.SolutionChild,BaseDataBindingHolder<ItemSolutionBinding>>(R.layout.item_solution,data) {


    override fun convert(holder: BaseDataBindingHolder<ItemSolutionBinding>, item: SolutionEntity.SolutionChild) {
        holder.dataBinding?.let {
            it.item = item
            it.itemSolutionDesTv.text = StringUtils.getHtmlString(item.desc)
        }
    }
}