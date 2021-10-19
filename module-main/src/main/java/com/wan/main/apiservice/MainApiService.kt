package com.wan.main.apiservice

import com.lilu.apptool.http.entity.BaseEntity
import com.wan.main.entity.BannerEntity
import com.wan.main.entity.VersionEntity
import com.wan.main.entity.WanMainEntity
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Description:
 * @author lilu0916 on 2021/6/21
 *  No one knows this better than me
 */
interface MainApiService {


    @GET("/banner/json")
    fun getBanner() : Observable<BaseEntity<MutableList<BannerEntity>>>

    @GET("/article/list/{pageNum}/json")
    fun getArticle(@Path("pageNum")pageNum:Int):Observable<BaseEntity<WanMainEntity>>

    /**
     * 版本更新
     */
    @GET("/abc")
    fun getNewVersion():Observable<BaseEntity<VersionEntity>>
}