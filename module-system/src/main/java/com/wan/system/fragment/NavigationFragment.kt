package com.wan.system.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.apptool.livedata.LiveStatus
import com.wan.system.R
import com.wan.system.adapter.SystemChildAdapter
import com.wan.system.entity.SystemEntity
import com.wan.system.model.SystemModel

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class NavigationFragment : BaseFragment() {

    private var system_rv : RecyclerView ?= null

    private var mAdapter : SystemChildAdapter ?= null
    private var data:MutableList<SystemEntity> = ArrayList()

    private val vm by lazy {
        ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(SystemModel::class.java)
    }

    override fun onVisibleFirst() {

        vm.getTree()

    }

    override fun getRootView(): Int {
        return R.layout.layout_system
    }

    override fun init(rootView: View) {

        system_rv = rootView.findViewById(R.id.system_rv)

        mAdapter = SystemChildAdapter(data)
        system_rv?.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = mAdapter
        }

        vm.treeList?.observe(this,{
            if(it.status == LiveStatus.SUCCESS){
                data.addAll(it.data)
                mAdapter?.notifyDataSetChanged()

                showSuccess()
            }
        })
    }
}