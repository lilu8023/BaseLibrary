package com.wan.solution.fragment

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lilu.appcommon.constant.Constants
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.apptool.livedata.LiveStatus
import com.lilu.apptool.router.RouterPath
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.wan.solution.R
import com.wan.solution.adapter.SolutionAdapter
import com.wan.solution.databinding.FragmentSolutionBinding
import com.wan.solution.entity.SolutionEntity
import com.wan.solution.model.SolutionModel
import com.wan.solution.router.SolutionRouterPath

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
@Route(path = RouterPath.FRAGMENT_SOLUTION)
class SolutionFragment : BaseFragment() {

    private var pageNum = 0

    private var viewBinding : FragmentSolutionBinding ?= null

    lateinit var mAdapter : SolutionAdapter
    private var mList = ArrayList<SolutionEntity.SolutionChild>()

    private val vm by lazy {
        ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(SolutionModel::class.java)
    }

    override fun onVisibleFirst() {
        showLoading()
        vm.getSolution(pageNum)

    }

    override fun getRootView(): Int {
        return R.layout.fragment_solution
    }

    override fun init(rootView: View) {

        viewBinding = DataBindingUtil.bind(rootView)
        //为xml中的控件添加点击事件
        viewBinding?.click = this
        //初始化列表适配器
        mAdapter = SolutionAdapter(mList)
        //列表item的点击事件
        mAdapter.setOnItemClickListener{_,_,position ->
            //列表点击之后跳转到WEBVIEW页面，并携带url
            ARouter.getInstance()
                    .build(RouterPath.ACTIVITY_WEB)
                    .withString(Constants.WEB_URL,mList[position].link)
                    .navigation()

        }
        viewBinding?.solutionSrl?.setOnRefreshLoadMoreListener(object: OnRefreshLoadMoreListener{
            override fun onRefresh(refreshLayout: RefreshLayout) {
                //下拉刷新

                pageNum = 0

                vm.getSolution(pageNum)

            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                //上拉加载更多

                pageNum++
                vm.getSolution(pageNum)
            }
        })
        //列表控件的设置
        viewBinding?.solutionRv?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
        //监听网络请求后的数据
        vm.solutionList.observe(this,{
            //关闭下拉刷新或上拉加载  没有标识是下拉还是上拉，所以干脆两个都设置finish
            viewBinding?.solutionSrl?.apply {
                finishRefresh()
                finishLoadMore()
            }
            //数据变化监听结果
            if(it.status == LiveStatus.SUCCESS){
                //网络请求成功
                if(!it.data.datas.isNullOrEmpty()){
                    //有数据
                    if(pageNum == 0){
                        if(mList.isEmpty()){
                            //第一次
                            mList.addAll(it.data.datas)
                            showSuccess()
                        }else{
                            //下拉刷新
                            mList.clear()
                            mList.addAll(it.data.datas)
                        }
                    }else{
                        //上拉加载更多
                        mList.addAll(it.data.datas)
                    }
                    mAdapter.notifyDataSetChanged()
                }else{
                    //无数据
                    if(pageNum == 0 && mList.isNullOrEmpty()){
                        //如果是第一次加载无数据就显示空
                        showEmpty()
                    }
                }
            }else{
                //网络请求失败
                if(pageNum == 0 && mList.isNullOrEmpty()){
                    //第一次网络请求失败，直接显示空
                    showEmpty()
                }
            }
        })
    }


    /**
     * 页面上控件的点击事件
     * @param v 控件
     */
    fun onViewClick(v:View){
        when(v.id){
            R.id.wan_notice_iv->{
                //消息点击事件
                ARouter.getInstance()
                        .build(SolutionRouterPath.ACTIVITY_NOTICE)
                        .navigation()
            }
        }

    }
}