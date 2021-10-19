package com.wan.main.entity

/**
 * Description:
 * @author lilu0916 on 2021/6/16
 *  No one knows this better than me
 */
data class WanMainEntity(var curPage: Int,
                         var offset: Int,
                         var over: Boolean,
                         var pageCount: Int,
                         var size: Int,
                         var total: Int,
                         var datas:MutableList<WanMainChild>) {

    data class WanMainChild(var author: String,
    var chapterName:String,
    var fresh:Boolean,
    var id:Int,
    var link:String,
    var niceDate:String,
    var superChapterName:String,
    var title:String)


}
