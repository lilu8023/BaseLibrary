package com.wan.solution.model

import androidx.lifecycle.ViewModel
import com.lilu.apptool.http.RetrofitHelper
import com.lilu.apptool.http.exception.ApiException
import com.lilu.apptool.http.subsciber.CommonSubscriber
import com.lilu.apptool.livedata.CustomLiveData
import com.wan.solution.apiservice.SolutionApiService
import com.wan.solution.entity.SolutionEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SolutionModel : ViewModel() {


    var solutionList = CustomLiveData<SolutionEntity>()

    fun getSolution(pageNum:Int){

        RetrofitHelper.getInstance()
                .create(SolutionApiService::class.java)
                .getSolution(pageNum)
                //线程切换 没有使用代理模式 必须要做线程切换
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CommonSubscriber<SolutionEntity>(){
                    override fun onError(e: ApiException?) {
                        e?.printStackTrace()
                    }


                    override fun onSuccess(data: SolutionEntity?) {

                        solutionList.postSuccess(data)

                    }
                })


    }

}