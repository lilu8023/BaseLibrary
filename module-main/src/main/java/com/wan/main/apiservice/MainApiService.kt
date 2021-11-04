package com.wan.main.apiservice

import com.lilu.apptool.http.entity.BaseEntity
import com.wan.main.entity.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Description:
 * @author lilu0916 on 2021/6/21
 *  No one knows this better than me
 */
interface MainApiService {


    /**
     * 首页 - 获取轮播图
     */
    @GET("/banner/json")
    fun getBanner() : Observable<BaseEntity<MutableList<BannerEntity>>>

    /**
     * 首页 - 获取常用工具
     */
    @GET("/friend/json")
    fun getTool():Observable<ToolEntity>

    /**
     * 首页 - 获取置顶文章列表
     */
    @GET("/article/top/json")
    fun getTopArticle():Observable<TopEntity>

    /**
     * 首页 - 获取文章列表
     */
    @GET("/article/list/{pageNum}/json")
    fun getArticle(@Path("pageNum")pageNum:Int):Observable<BaseEntity<WanMainEntity>>

    /**
     * 版本更新
     */
    @GET("/abc")
    fun getNewVersion():Observable<BaseEntity<VersionEntity>>
}