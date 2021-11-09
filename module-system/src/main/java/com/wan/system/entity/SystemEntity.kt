package com.wan.system.entity

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SystemEntity{

    var courseId:Int = 0
    var name:String = ""
    var children:MutableList<SystemChild> ?= null

    class SystemChild{

        var id:Int = 0
        var name:String = ""
    }
}
