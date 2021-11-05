package com.wan.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.appcommon.widget.recyclerview.GridSpaceItemDecoration
import com.lilu.appcommon.widget.recyclerview.SpacesItemDecoration
import com.lilu.apptool.imageloader.ImageLoader
import com.lilu.apptool.livedata.CustomLiveData
import com.lilu.apptool.livedata.LiveStatus
import com.lilu.apptool.router.Router
import com.lilu.apptool.router.RouterPath
import com.wan.main.R
import com.wan.main.adapter.WanMainAdapter
import com.wan.main.adapter.WanToolAdapter
import com.wan.main.databinding.FragmentWanBinding
import com.wan.main.entity.BannerEntity
import com.wan.main.entity.ToolEntity
import com.wan.main.entity.WanMainEntity
import com.wan.main.model.MainModel
import com.wan.main.router.MainRouterPath
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import java.io.Serializable

/**
 * Description:
 * 首页的fragment
 * @author lilu0916 on 2021/6/11
 *  No one knows this better than me
 */
@Route(path = RouterPath.FRAGMENT_MAIN)
class WanMainFragment : BaseFragment() {

    lateinit var bannerHeadView:View
    //轮播图适配器
    private var bannerAdapter:BannerImageAdapter<BannerEntity> ?= null
    //轮播图数据
    private var bannerList = ArrayList<BannerEntity>()

    lateinit var toolView:View
    private lateinit var toolAdapter:WanToolAdapter
    private var toolList= ArrayList<ToolEntity.ToolChild>()

    //首页文章列表适配器
    private lateinit var wanAdapter : WanMainAdapter
    //首页文章数据
    private var wanList = ArrayList<WanMainEntity.WanMainChild>()

    private lateinit var wanBinding:FragmentWanBinding

    private lateinit var mainVm:MainModel

    override fun onVisibleFirst() {

        mainVm.getMainBanner()

        mainVm.getMainTool()

        mainVm.getMainTopArticle()

        mainVm.getMainArticle()
    }

    override fun getRootView(): Int {
        return R.layout.fragment_wan
    }

    override fun init(rootView: View) {
        wanBinding = DataBindingUtil.bind(rootView)!!

        bannerHeadView = layoutInflater.inflate(R.layout.layout_main_head,null)

        toolView = layoutInflater.inflate(R.layout.layout_main_tools,null)

        mainVm = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainModel::class.java)

        wanAdapter = WanMainAdapter(wanList)
        wanAdapter.addHeaderView(bannerHeadView)
        wanAdapter.addHeaderView(toolView)

        wanBinding.rvFraWan.let {
            it.addItemDecoration(SpacesItemDecoration(activity))
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = wanAdapter
        }

        wanAdapter.setOnItemClickListener { _, _, position ->

            run {
                Toast.makeText(activity, "点击了${wanList[position].title}", Toast.LENGTH_SHORT).show()
            }

        }
        initBanner()

        initTool()

        initArticle()

    }

    /**
     * 初始化轮播图
     */
    private fun initBanner(){
        bannerAdapter = object : BannerImageAdapter<BannerEntity>(bannerList) {
            override fun onBindView(holder: BannerImageHolder?, data: BannerEntity?, position: Int, size: Int) {
                holder?.let {
                    ImageLoader.getInstance()
                            .displayImage(activity, bannerList[position].imagePath, it.imageView)
                }
            }
        }
        bannerHeadView.findViewById<Banner<*,*>>(R.id.banner_fra_wan).apply {

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

    private fun initTool(){
        toolAdapter = WanToolAdapter(toolList)

        var toolRv = toolView.findViewById<RecyclerView>(R.id.rv_main_tools)
        toolRv?.let {
            it.addItemDecoration(GridSpaceItemDecoration(5))
            it.layoutManager = GridLayoutManager(activity,4)
            it.adapter = toolAdapter
        }

        mainVm.toolList.observe(this,{
            if (it.status == LiveStatus.SUCCESS){
                toolList.clear()
                toolList.addAll(it.data)
                toolAdapter.notifyDataSetChanged()
            }
        })

        toolAdapter.setOnItemClickListener { _, _, position ->
            if (position != 7) {
                //直接跳转到相应的工具页
                Toast.makeText(activity, "点击了${toolList[position].name}", Toast.LENGTH_SHORT).show()
            } else {
                //点击了更多，打开更多工具页

                ARouter.getInstance()
                        .build(MainRouterPath.ACTIVITY_TOOL)
                        .navigation()
            }
        }
    }
    /**
     * 初始化首页文章列表
     */
    private fun initArticle(){

        mainVm.topArticleList.observe(this, { t ->
            t?.let {
                wanList.addAll(0,it.data)
                wanAdapter.notifyDataSetChanged()

                showSuccess()
            }
        })

        mainVm.articleList.observe(this, { t ->
            t?.let {
                wanList.addAll(it.datas)
                wanAdapter.notifyDataSetChanged()
            }
        })
    }

}