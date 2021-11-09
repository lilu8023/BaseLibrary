package com.wan.system.router

/**
 * Description:
 * @author lilu0916 on 2021/11/9
 *  No one knows this better than me
 */
class SystemRouterPath {

    companion object{
        /**
         * 体系的二级分类页面的路由以及页面需要传递的参数
         * POSITION 选中项
         * TITLE 标题
         */
        const val ACTIVITY_SYSTEM_CHILD = "/system/activity_system_child"
        const val POSITION = "position"
        const val TITLE = "title"
        const val TAB_LIST = "tab_list"

        const val FRAGMENT_SYSTEM_CHILD = "/system/fragment_system_child"
        const val SYSTEM_CHILD_ID = "id"
    }
}