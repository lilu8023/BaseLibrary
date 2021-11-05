package com.wan.solution.apiservice

import com.lilu.apptool.http.entity.BaseEntity
import com.wan.solution.entity.SolutionEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
interface SolutionApiService {

    @GET("/wenda/list/{pageNum}/json")
    fun getSolution(@Path("pageNum")pageNUm:Int):Observable<BaseEntity<SolutionEntity>>
}