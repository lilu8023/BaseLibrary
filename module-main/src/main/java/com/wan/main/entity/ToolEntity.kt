package com.wan.main.entity

/**
 * Description:
 * @author lilu0916 on 2021/11/3
 *  No one knows this better than me
 */
data class ToolEntity(var data: MutableList<ToolChild>) {

    data class ToolChild(var category: String,
                         var icon: String,
                         var id: Int,
                         var link: String,
                         var name: String,
                         var order: Int,
                         var visible: Int)

}
