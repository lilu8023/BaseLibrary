package com.wan.system.model

import androidx.lifecycle.ViewModel
import com.lilu.apptool.http.RetrofitHelper
import com.lilu.apptool.http.entity.BaseEntity
import com.lilu.apptool.http.exception.ApiException
import com.lilu.apptool.http.subsciber.CommonSubscriber
import com.lilu.apptool.livedata.CustomLiveData
import com.wan.system.apiservice.SystemApiService
import com.wan.system.entity.SystemEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SystemModel : ViewModel() {

    var treeList = CustomLiveData<MutableList<SystemEntity>>()

    fun getTree(){

        RetrofitHelper.getInstance()
                .create(SystemApiService::class.java)
                .getTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :CommonSubscriber<MutableList<SystemEntity>>(){
                    override fun onError(e: ApiException?) {
                    }

                    override fun onSuccess(data: MutableList<SystemEntity>?) {
                        treeList.postSuccess(data)
                    }
                })
    }
}