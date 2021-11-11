package com.wan.solution.entity

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
data class SolutionEntity(var curPage: Int,
                          var offset: Int,
                          var over: Boolean,
                          var pageCount: Int,
                          var size: Int,
                          var total: Int,
                          var datas: MutableList<SolutionChild>) {

    data class SolutionChild(var title: String,
                             var link: String,
                             var author: String,
                             var superChapterName: String,
                             var chapterName: String,
                             var desc: String,
                             var niceDate: String)


}
