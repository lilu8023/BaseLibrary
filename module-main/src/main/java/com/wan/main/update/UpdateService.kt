package com.wan.main.update

import com.google.gson.GsonBuilder
import com.lilu.apptool.gson.DeserializeActionFactory
import com.lilu.apptool.gson.NonNullFieldFactory
import com.lilu.apptool.http.exception.ApiException
import com.lilu.apptool.http.subsciber.CommonSubscriber
import com.lilu.apptool.update.entity.UpdateEntity
import com.lilu.apptool.update.proxy.IUpdateHttpService
import com.wan.main.apiservice.MainApiService
import com.wan.main.entity.BannerEntity
import com.wan.main.entity.VersionEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Description:
 * @author lilu0916 on 2021/8/12
 *  No one knows this better than me
 */
class UpdateService : IUpdateHttpService {

    override fun haveNewVersion(callBack: IUpdateHttpService.Callback) {

        val gson2 = GsonBuilder()
                .registerTypeAdapterFactory(NonNullFieldFactory())
                .registerTypeAdapterFactory(DeserializeActionFactory())
                .create()

        //创建retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.abcd.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson2))
                .build()

        //创建网络请求接口
        val mainApi = retrofit.create(MainApiService::class.java)

        //对发送请求进行封装
        val bannerResponse = mainApi.getNewVersion()

        //发送网络异步请求
        bannerResponse.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: CommonSubscriber<VersionEntity>(){
                    override fun onError(e: ApiException?) {
                        callBack.onError(Throwable(e?.message))
                    }

                    override fun onSuccess(data: VersionEntity?) {

                        val updateEntity = UpdateEntity()

                        data?.let {

                            updateEntity.versionCode = it.code
                            updateEntity.updateContent = it.content

                            callBack.onSuccess(updateEntity)
                        }
                    }


                })

    }

    override fun download(url: String, path: String, fileName: String, callback: IUpdateHttpService.DownloadCallback) {
        TODO("Not yet implemented")
    }

    override fun cancelDownload(url: String) {
        TODO("Not yet implemented")
    }
}