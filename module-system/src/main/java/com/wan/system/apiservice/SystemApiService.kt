package com.wan.system.apiservice

import com.lilu.apptool.http.entity.BaseEntity
import com.wan.system.entity.HierarchyEntity
import com.wan.system.entity.SystemEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
interface SystemApiService {

    @GET("/tree/json")
    fun getTree():Observable<BaseEntity<MutableList<SystemEntity>>>

    @GET("/article/list/{pageNum}/json")
    fun getHierarchy(@Path("pageNum")pageNum:Int,
                     @Query("cid")cid:Int):Observable<BaseEntity<HierarchyEntity>>
}