package com.wan.solution.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wan.solution.R
import com.wan.solution.entity.SolutionEntity

/**
 * Description:
 * 首页 - 问答适配器
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SolutionAdapter(data:MutableList<SolutionEntity.SolutionChild>) : BaseQuickAdapter<SolutionEntity.SolutionChild,BaseViewHolder>(R.layout.item_solution,data) {


    override fun convert(holder: BaseViewHolder, item: SolutionEntity.SolutionChild) {

        holder.setText(R.id.item_solution_title_tv,item.title)

    }
}