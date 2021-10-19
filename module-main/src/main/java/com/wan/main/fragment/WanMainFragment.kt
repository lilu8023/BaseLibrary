package com.wan.main.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.appcommon.widget.recyclerview.SpacesItemDecoration
import com.lilu.apptool.imageloader.ImageLoader
import com.lilu.apptool.livedata.LiveStatus
import com.wan.main.R
import com.wan.main.adapter.WanBannerAdapter
import com.wan.main.adapter.WanMainAdapter
import com.wan.main.databinding.FragmentWanBinding
import com.wan.main.entity.BannerEntity
import com.wan.main.entity.WanMainEntity
import com.wan.main.model.MainModel
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.listener.OnBannerListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Description:
 * @author lilu0916 on 2021/6/11
 *  No one knows this better than me
 */
class WanMainFragment : BaseFragment() {

    //轮播图适配器
    private var bannerAdapter:BannerImageAdapter<BannerEntity> ?= null
    //轮播图数据
    private var bannerList = ArrayList<BannerEntity>()
    //首页文章列表适配器
    private lateinit var wanAdapter : WanMainAdapter
    //首页文章数据
    private var wanList = ArrayList<WanMainEntity.WanMainChild>()

    private lateinit var wanBinding:FragmentWanBinding

    private lateinit var mainVm:MainModel

    override fun onVisibleFirst() {

        Observable.timer(3,TimeUnit.SECONDS)
                .subscribe(object : Observer<Long>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Long) {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                        showSuccess()
                    }
                })

        mainVm.getMainBanner()

        mainVm.getMainArticle()
    }

    override fun getRootView(): Int {
        return R.layout.fragment_wan
    }

    override fun init(rootView: View) {
        wanBinding = DataBindingUtil.bind(rootView)!!

        mainVm = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainModel::class.java)

        initBanner()

        initArticle()

    }

    /**
     * 初始化轮播图
     */
    private fun initBanner(){
//        bannerAdapter = WanBannerAdapter(bannerList)
        bannerAdapter = object : BannerImageAdapter<BannerEntity>(bannerList) {
            override fun onBindView(holder: BannerImageHolder?, data: BannerEntity?, position: Int, size: Int) {
                holder?.let {
                    ImageLoader.getInstance()
                            .displayImage(activity, bannerList[position].imagePath, it.imageView)
                }


            }

        }
        wanBinding.bannerFraWan.apply {

            adapter = bannerAdapter

            setOnBannerListener { data, position ->
                Toast.makeText(activity, "点击了$position", Toast.LENGTH_SHORT).show()
            }
        }

        mainVm.bannerList.observe(this,{ t->
            run {
                if(t.status == LiveStatus.SUCCESS){
                    bannerList.clear()
                    bannerList.addAll(t.data)
                    bannerAdapter?.notifyDataSetChanged()
                }else{
                    Log.i("lilu","错误了:${t.error.message}")
                }
            }
        })


    }

    /**
     * 初始化首页文章列表
     */
    private fun initArticle(){
        wanAdapter = WanMainAdapter(wanList)

        wanBinding.rvFraWan.let {
            it.addItemDecoration(SpacesItemDecoration(activity))
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = wanAdapter
        }

        mainVm.articleList.observe(this, { t ->
            t?.let {
                wanList.addAll(it.datas)
                wanAdapter.notifyDataSetChanged()
            }
        })
    }

}