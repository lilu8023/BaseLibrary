package com.wan.main.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.appcommon.widget.recyclerview.GridSpaceItemDecoration
import com.lilu.appcommon.widget.recyclerview.SpacesItemDecoration
import com.lilu.apptool.imageloader.ImageLoader
import com.lilu.apptool.livedata.LiveStatus
import com.lilu.apptool.router.RouterPath
import com.orhanobut.logger.Logger
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

/**
 * Description:
 * 首页的fragment
 * @author lilu0916 on 2021/6/11
 *  No one knows this better than me
 */
@Route(path = RouterPath.FRAGMENT_MAIN)
class WanMainFragment : BaseFragment(),View.OnClickListener {

    /**
     * 轮播图控件
     * 轮播图适配器
     * 轮播图数据
     */
    lateinit var bannerHeadView:View
    private var bannerAdapter:BannerImageAdapter<BannerEntity> ?= null
    private var bannerList = ArrayList<BannerEntity>()

    /**
     * 常用网站
     * 常用网站适配器
     * 常用网站数据
     */
    lateinit var toolView:View
    private lateinit var toolAdapter:WanToolAdapter
    private var toolList= ArrayList<ToolEntity.ToolChild>()

    /**
     * 首页文章列表适配器
     * 首页文章数据(根据情况需要添加置顶文章)
     */
    private lateinit var wanAdapter : WanMainAdapter
    private var wanList = ArrayList<WanMainEntity.WanMainChild>()

    /**
     * databinding工具嘞
     */
    private lateinit var wanBinding:FragmentWanBinding

    /**
     * viewmodel获取网络数据工具类
     */
    private lateinit var mainVm:MainModel

    /**
     * fragment第一次显示时调用
     */
    override fun onVisibleFirst() {
        //获取轮播图
        mainVm.getMainBanner()
        //获取常用工具
        mainVm.getMainTool()
        //获取置顶文章
        mainVm.getMainTopArticle()
        //获取文章列表
        mainVm.getMainArticle()
    }

    override fun getRootView() = R.layout.fragment_wan

    override fun init(rootView: View) {
        //初始化databinding工具类
        wanBinding = DataBindingUtil.bind(rootView)!!
        //加载轮播图控件  作为headview加入
        bannerHeadView = layoutInflater.inflate(R.layout.layout_main_head,null)
        //加载常用工具类控件  作为headview加入
        toolView = layoutInflater.inflate(R.layout.layout_main_tools,null)
        //初始化viewmodel工具
        mainVm = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainModel::class.java)
        //整个列表的适配器
        wanAdapter = WanMainAdapter(wanList)
        //列表加入轮播图和常用工具作为headview
        wanAdapter.addHeaderView(bannerHeadView)
        wanAdapter.addHeaderView(toolView)
        //列表设置
        wanBinding.rvFraWan.let {
            //添加分割线
            it.addItemDecoration(SpacesItemDecoration(activity))
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = wanAdapter
        }
        //给xml中的控件设置点击事件
        wanBinding.click = this
        //列表item设置点击事件
        wanAdapter.setOnItemClickListener { _, _, position ->
            //跳转到webview页面，并携带URL地址
            ARouter.getInstance()
                    .build(RouterPath.ACTIVITY_WEB)
                    .withString(RouterPath.WEB_URL,wanList[position].link)
                    .navigation()
        }

        /**
         * 初始化轮播图
         * 初始化常用工具
         * 初始化文章列表
         */
        initBanner()
        initTool()
        initArticle()

    }

    /**
     * 初始化轮播图
     */
    private fun initBanner(){
        //轮播图适配器初始化
        bannerAdapter = object : BannerImageAdapter<BannerEntity>(bannerList) {
            override fun onBindView(holder: BannerImageHolder?, data: BannerEntity?, position: Int, size: Int) {
                holder?.let {
                    //给轮播图item加载图片
                    ImageLoader.getInstance()
                            .displayImage(activity, bannerList[position].imagePath, it.imageView)
                }
            }
        }
        //获取轮播图控件并设置适配器与点击事件
        bannerHeadView.findViewById<Banner<*,*>>(R.id.banner_fra_wan).apply {

            adapter = bannerAdapter

            setOnBannerListener { _, position ->
                ARouter.getInstance()
                        .build(RouterPath.ACTIVITY_WEB)
                        .withString(RouterPath.WEB_URL,bannerList[position].url)
                        .navigation()
            }
        }
        //监听轮播图数据的变化
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
                ARouter.getInstance()
                        .build(RouterPath.ACTIVITY_WEB)
                        .withString(RouterPath.WEB_URL,toolList[position].link)
                        .navigation()
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

    fun myClick(v:View){
        Logger.i("点击了搜索按钮")
    }
    override fun onClick(v: View?) {


    }

}