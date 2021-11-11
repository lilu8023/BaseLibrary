package com.wan.system.fragment

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.apptool.livedata.LiveStatus
import com.lilu.apptool.router.RouterPath
import com.orhanobut.logger.Logger
import com.wan.system.R
import com.wan.system.adapter.SystemItemAdapter
import com.wan.system.databinding.FragmentSystemChildBinding
import com.wan.system.entity.HierarchyEntity
import com.wan.system.model.SystemModel
import com.wan.system.router.SystemRouterPath

/**
 * Description:
 * @author lilu0916 on 2021/11/9
 *  No one knows this better than me
 */
@Route(path = SystemRouterPath.FRAGMENT_SYSTEM_CHILD)
class SystemChildFragment : BaseFragment() {

    @Autowired(name = SystemRouterPath.SYSTEM_CHILD_ID)
    @JvmField
    var systemId:Int = 0

    private var viewBinding:FragmentSystemChildBinding ?= null

    private lateinit var vm:SystemModel

    private var mAdapter : SystemItemAdapter?= null
    private var mList:MutableList<HierarchyEntity.HierarchyChild> = ArrayList()


    override fun onVisibleFirst() {
        Logger.i("firstVisible")
        showLoading()
        vm.getHierarchy(0,systemId)
    }

    override fun getRootView() = R.layout.fragment_system_child

    override fun init(rootView: View) {

        ARouter.getInstance().inject(this)

        viewBinding = DataBindingUtil.bind(rootView)

        vm = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(SystemModel::class.java)
        vm.hierarchy.observe(this,{

            if(it.status == LiveStatus.SUCCESS){
                mList.addAll(it.data.datas)
                mAdapter?.notifyDataSetChanged()

                showSuccess()
            }else{
                showEmpty()
            }

        })

        mAdapter = SystemItemAdapter(mList)
        //列表点击事件 携带H5地址跳转到H5页面
        mAdapter?.setOnItemClickListener{_,_,position->
            ARouter.getInstance()
                    .build(RouterPath.ACTIVITY_WEB)
                    .withString(RouterPath.WEB_URL,mList[position].link)
                    .navigation()
        }
        viewBinding?.systemChildRv?.let {

            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = mAdapter
        }

    }
}