package com.wan.system.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lilu.appcommon.widget.flowlayout.FlowLayout
import com.lilu.appcommon.widget.flowlayout.TagAdapter
import com.wan.system.R
import com.wan.system.databinding.ItemSystemBinding
import com.wan.system.entity.SystemEntity
import com.wan.system.router.SystemRouterPath

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SystemChildAdapter(data:MutableList<SystemEntity>):BaseQuickAdapter<SystemEntity,BaseDataBindingHolder<ItemSystemBinding>>(R.layout.item_system,data) {


    override fun convert(holder: BaseDataBindingHolder<ItemSystemBinding>, item: SystemEntity) {


        holder.dataBinding?.let {
            it.fatherInfo = item

            it.itemSystemTfl.adapter = object : TagAdapter<SystemEntity.SystemChild>(item.children) {
                override fun getView(parent: FlowLayout?, position: Int, t: SystemEntity.SystemChild?): View {

                    val systemChildView = LayoutInflater.from(context).inflate(R.layout.item_system_child,null,false)
                    val textView:TextView = systemChildView.findViewById(R.id.item_system_child_tv)
                    textView.text = t?.name

                    return systemChildView
                }

            }

            it.itemSystemTfl.setOnTagClickListener { _, position, _ ->

                //点击之后跳转到二级分类页
                ARouter.getInstance()
                        .build(SystemRouterPath.ACTIVITY_SYSTEM_CHILD)
                        //携带选中项
                        .withInt(SystemRouterPath.POSITION,position)
                        .withString(SystemRouterPath.TITLE,item.name)
                        .withObject(SystemRouterPath.TAB_LIST,item.children)
                        .navigation()

                true
            }
        }

    }
}