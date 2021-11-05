package com.wan.solution.fragment

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lilu.appcommon.constant.Constants
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.apptool.router.RouterPath
import com.wan.solution.R
import com.wan.solution.adapter.SolutionAdapter
import com.wan.solution.databinding.FragmentSolutionBinding
import com.wan.solution.entity.SolutionEntity
import com.wan.solution.model.SolutionModel

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
@Route(path = RouterPath.FRAGMENT_SOLUTION)
class SolutionFragment : BaseFragment() {


    private var viewBinding : FragmentSolutionBinding ?= null

    lateinit var mAdapter : SolutionAdapter
    private var mList = ArrayList<SolutionEntity.SolutionChild>()

    private val vm by lazy {
        ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(SolutionModel::class.java)
    }

    override fun onVisibleFirst() {

        vm.getSolution(1)

    }

    override fun getRootView(): Int {
        return R.layout.fragment_solution
    }

    override fun init(rootView: View) {

        viewBinding = DataBindingUtil.bind(rootView)

        mAdapter = SolutionAdapter(mList)
        //列表item的点击事件
        mAdapter.setOnItemClickListener{_,_,position ->

            ARouter.getInstance()
                    .build(RouterPath.ACTIVITY_WEB)
                    .withString(Constants.WEB_URL,mList[position].link)
                    .navigation()

        }
        viewBinding?.solutionRv?.apply {

            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

        vm.solutionList?.observe(this,{
            mList.addAll(it.data.datas)

            mAdapter.notifyDataSetChanged()

            showSuccess()
        })
    }
}