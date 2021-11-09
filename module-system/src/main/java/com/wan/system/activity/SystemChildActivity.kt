package com.wan.system.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayoutMediator
import com.lilu.appcommon.activity.BaseActivity
import com.lilu.appcommon.adapter.ViewPageAdapter
import com.lilu.appcommon.adapter.ViewPagerAdapter
import com.orhanobut.logger.Logger
import com.wan.system.R
import com.wan.system.databinding.ActivitySystemChildBinding
import com.wan.system.entity.SystemEntity
import com.wan.system.router.SystemRouterPath

/**
 * Description:
 * 体系二级页
 * @author lilu0916 on 2021/11/9
 *  No one knows this better than me
 */
@Route(path = SystemRouterPath.ACTIVITY_SYSTEM_CHILD)
class SystemChildActivity :BaseActivity() {


    @JvmField
    @Autowired(name = SystemRouterPath.TITLE)
    var tbTitle:String = ""
    @JvmField
    @Autowired(name = SystemRouterPath.POSITION)
    var mPosition:Int = 0
    @JvmField
    @Autowired(name = SystemRouterPath.TAB_LIST)
    var tabList: MutableList<SystemEntity.SystemChild>?= null


    private var viewBinding:ActivitySystemChildBinding ?= null
    private var mAdapter : ViewPageAdapter?= null
    private var mFragments:ArrayList<Fragment> ?= null


    override fun getContentView() = R.layout.activity_system_child

    override fun init(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this)

        setTitle(tbTitle)

        viewBinding = DataBindingUtil.bind(mContentView)

        mFragments = ArrayList()



        mAdapter = ViewPageAdapter(this,mFragments)
        if(!tabList.isNullOrEmpty()) {
            for (item:SystemEntity.SystemChild in tabList!!){

                mFragments?.apply {
                    add(ARouter.getInstance()
                            .build(SystemRouterPath.FRAGMENT_SYSTEM_CHILD)
                            .withInt(SystemRouterPath.SYSTEM_CHILD_ID,item.id)
                            .navigation() as Fragment)

                }
            }

            viewBinding?.let {

                it.systemChildVp.adapter = mAdapter
                TabLayoutMediator(it.systemChildTab,it.systemChildVp,true) { tab, position ->
                    tab.text = tabList?.get(position)?.name
                }.attach()
                //定位在上一页面点击的项
                it.systemChildVp.currentItem = mPosition
            }
            showSuccess()
        }else{
            showEmpty()
        }
    }
}