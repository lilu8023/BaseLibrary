package com.wan.system.fragment

import android.view.View
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.apptool.router.RouterPath
import com.orhanobut.logger.Logger
import com.wan.system.R
import com.wan.system.adapter.SystemAdapter
import com.wan.system.databinding.FragmentSystemBinding

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
@Route(path = RouterPath.FRAGMENT_SYSTEM)
class SystemFragment : BaseFragment() {

    private var viewBinding : FragmentSystemBinding ?= null

    private var mAdapter : SystemAdapter ?= null

    private val tabs = arrayOf("体系","导航")

    override fun onVisibleFirst() {

        showSuccess()
    }

    override fun getRootView(): Int {
        return R.layout.fragment_system
    }

    override fun init(rootView: View) {

        viewBinding = DataBindingUtil.bind(rootView)

        mAdapter = SystemAdapter(this,tabs)

        viewBinding?.let {
            TabLayoutMediator(it.systemTb,it.systemVp,true) { tab, position -> Logger.i("选中了${position}") }.attach()
        }



    }
}