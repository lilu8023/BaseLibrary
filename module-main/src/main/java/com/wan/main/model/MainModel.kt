package com.wan.main.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.lilu.apptool.gson.DeserializeActionFactory
import com.lilu.apptool.gson.NonNullFieldFactory
import com.lilu.apptool.http.exception.ApiException
import com.lilu.apptool.http.subsciber.CommonSubscriber
import com.lilu.apptool.livedata.CustomLiveData
import com.wan.main.adapter.WanBannerAdapter
import com.wan.main.apiservice.MainApiService
import com.wan.main.entity.BannerEntity
import com.wan.main.entity.WanMainEntity
import com.youth.banner.listener.OnBannerListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Description:
 * @author lilu0916 on 2021/7/1
 *  No one knows this better than me
 */
class MainModel : ViewModel() {

    //首页轮播图数据
    var bannerList = CustomLiveData<MutableList<BannerEntity>>()
    //首页文章数据
    var articleList = MutableLiveData<WanMainEntity>()

    fun getMainBanner(){

        val gson2 = GsonBuilder()
                .registerTypeAdapterFactory(NonNullFieldFactory())
                .registerTypeAdapterFactory(DeserializeActionFactory())
                .create()

        //创建retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson2))
                .build()

        //创建网络请求接口
        val mainApi = retrofit.create(MainApiService::class.java)

        //对发送请求进行封装
        val bannerResponse = mainApi.getBanner()

        //发送网络异步请求
        bannerResponse.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: CommonSubscriber<MutableList<BannerEntity>>(){
                    override fun onError(e: ApiException?) {
//                        e?.printStackTrace()
                        bannerList.postError(Throwable(e?.message))
                    }

                    override fun onSuccess(data: MutableList<BannerEntity>?) {
                        bannerList.postSuccess(data)
                    }
                })


    }
    fun getMainArticle(){
        val gson2 = GsonBuilder()
                .registerTypeAdapterFactory(NonNullFieldFactory())
                .registerTypeAdapterFactory(DeserializeActionFactory())
                .create()

        //创建retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson2))
                .build()

        //创建网络请求接口
        val mainApi = retrofit.create(MainApiService::class.java)

        //对发送请求进行封装
        val bannerResponse = mainApi.getArticle(0)

        //发送网络异步请求
        bannerResponse.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: CommonSubscriber<WanMainEntity>(){
                    override fun onSuccess(t: WanMainEntity?) {
                        articleList.postValue(t)
                    }

                    override fun onError(e: ApiException?) {

                    }
                })
    }
}