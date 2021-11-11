package com.wan.system.fragment

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lilu.appcommon.fragment.BaseFragment
import com.wan.system.R
import com.wan.system.adapter.TreeAdapter
import com.wan.system.databinding.FragmentSystemChildBinding
import com.wan.system.entity.NavigationEntity
import com.wan.system.model.SystemModel

/**
 * Description:
 * 导航
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class TreeFragment :BaseFragment() {

    private lateinit var viewBinding:FragmentSystemChildBinding
    private lateinit var vm : SystemModel
    private lateinit var mAdapter : TreeAdapter

    private var mList:MutableList<NavigationEntity> = ArrayList()
    override fun onVisibleFirst() {
        vm.getNavigation()
    }

    override fun getRootView(): Int {
        return R.layout.fragment_system_child
    }

    override fun init(rootView: View) {

        viewBinding = DataBindingUtil.bind(rootView)!!
        vm = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(SystemModel::class.java)

        vm.navigationList.observe(this,{
            mList.addAll(it.data)
            mAdapter.notifyDataSetChanged()
            showSuccess()
        })

        mAdapter = TreeAdapter(mList)

        viewBinding.systemChildRv.apply {

            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }
}