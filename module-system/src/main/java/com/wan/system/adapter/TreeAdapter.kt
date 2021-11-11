package com.wan.system.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lilu.appcommon.widget.flowlayout.FlowLayout
import com.lilu.appcommon.widget.flowlayout.TagAdapter
import com.lilu.appcommon.widget.flowlayout.TagFlowLayout
import com.lilu.apptool.router.RouterPath
import com.wan.system.R
import com.wan.system.entity.NavigationEntity
import com.wan.system.entity.SystemEntity
import com.wan.system.router.SystemRouterPath

/**
 * Description:
 * @author lilu0916 on 2021/11/10
 *  No one knows this better than me
 */
class TreeAdapter(data:MutableList<NavigationEntity>): BaseQuickAdapter<NavigationEntity,BaseViewHolder>(R.layout.item_tree,data) {

    override fun convert(holder: BaseViewHolder, item: NavigationEntity) {
        holder.setText(R.id.item_tree_name_tv,item.name)

        val tagFlow = holder.getView<TagFlowLayout>(R.id.item_tree_tfl)
        tagFlow.adapter = object : TagAdapter<NavigationEntity.NavigationChild>(item.articles) {
            override fun getView(parent: FlowLayout?, position: Int, t: NavigationEntity.NavigationChild?): View {

                val systemChildView = LayoutInflater.from(context).inflate(R.layout.item_system_child,null,false)
                val textView: TextView = systemChildView.findViewById(R.id.item_system_child_tv)
                textView.text = t?.title

                return systemChildView
            }

        }

        tagFlow.setOnTagClickListener { _, position, _ ->

                    //点击之后跳转到二级分类页
                    ARouter.getInstance()
                            .build(RouterPath.ACTIVITY_WEB)
                            //携带选中项
                            .withString(RouterPath.WEB_URL,item.articles[position].link)
                            .navigation()

                    true
                }

    }
}