package com.wan.main.entity

/**
 * Description:
 * 首页 - 文章列表
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

    /**
     * 首页 - 文章的详情
     */
    data class WanMainChild(var author: String,
    var chapterName:String,
    var fresh:Boolean,
    var id:Int,
    var link:String,
    var niceDate:String,
    var superChapterName:String,
    var title:String,
    var type:Int,
    var desc:String)


}
