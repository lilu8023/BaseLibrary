package com.wan.system.entity

import com.google.gson.annotations.SerializedName

/**
 * Description:
 * @author lilu0916 on 2021/11/9
 *  No one knows this better than me
 */
data class HierarchyEntity(var datas:MutableList<HierarchyChild>) {
    data class HierarchyChild(var title:String, var link:String)
}