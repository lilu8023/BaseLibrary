package com.wan.system.entity

/**
 * Description:
 * @author lilu0916 on 2021/11/10
 *  No one knows this better than me
 */
data class NavigationEntity(var name:String,var articles:MutableList<NavigationChild>) {

    data class NavigationChild(var title:String,var link:String)
}