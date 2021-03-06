package com.wan.main.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lilu.appcommon.activity.BaseActivity
import com.lilu.appcommon.adapter.ViewPagerAdapter
import com.lilu.appcommon.widget.jptabbar.JPTabBar
import com.lilu.appcommon.widget.jptabbar.OnTabSelectListener
import com.lilu.appcommon.widget.jptabbar.bean.TabBean
import com.lilu.apptool.router.RouterPath
import com.wan.main.R
import com.wan.main.fragment.WanMainFragment
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


/**
 * Description:
 * 玩Android的首页
 * @author lilu0916 on 2021/6/11
 *  No one knows this better than me
 */
@Route(path = RouterPath.ACTIVITY_MAIN)
class WanMainActivity : BaseActivity() {

    private lateinit var vpWan:ViewPager2
    private lateinit var tab_wan:JPTabBar
    private lateinit var vpAdapter: ViewPagerAdapter
    private lateinit var vpList:MutableList<Fragment>

    private val mTitles = arrayOf("首页", "问答", "体系", "我的")

    private val mNormalIcons = intArrayOf(R.drawable.ic_bottom_bar_home, R.drawable.ic_bottom_bar_ques, R.drawable.ic_bottom_bar_navi, R.drawable.ic_bottom_bar_mine)

    private val mSelectIcons = intArrayOf(R.drawable.ic_bottom_bar_home, R.drawable.ic_bottom_bar_ques, R.drawable.ic_bottom_bar_navi, R.drawable.ic_bottom_bar_mine)

    override fun getContentView(): Int {
        return R.layout.activity_wan
    }

    override fun init(savedInstanceState: Bundle?) {
        //首页不显示toolbar
        showToolbar(false)
        vpWan = findViewById(R.id.vp_wan)
        tab_wan = findViewById(R.id.tab_wan)

        vpList = ArrayList()
        vpList.apply {
            add(WanMainFragment())
            //添加问答 采用路由获取 并强转成fragment
            add(ARouter.getInstance().build(RouterPath.FRAGMENT_SOLUTION).navigation() as Fragment)
            //添加体系 采用路由获取 并强转成fragment
            add(ARouter.getInstance().build(RouterPath.FRAGMENT_SYSTEM).navigation() as Fragment)
            //添加个人中心  采用路由获取 并强转成fragment
            add(ARouter.getInstance().build(RouterPath.FRAGMENT_MINE).navigation() as Fragment)
        }

        vpAdapter = ViewPagerAdapter(this@WanMainActivity, vpList)

        vpWan.adapter = vpAdapter

        var tabBean1:TabBean = TabBean()
        var tabBean2:TabBean = TabBean()
        var tabBean3:TabBean = TabBean()
        var tabBean4:TabBean = TabBean()

        tabBean1.apply {

            title = "首页"
            defaultNormal = R.drawable.ic_bottom_bar_home
            defaultSelect = R.drawable.ic_bottom_bar_home
            iconNormal = ""
            iconSelect = ""
            router = "我是首页路由"

        }

        tabBean2.apply {

            title = "问答"
            defaultNormal = R.drawable.ic_bottom_bar_ques
            defaultSelect = R.drawable.ic_bottom_bar_ques
            iconNormal = ""
            iconSelect = ""
            router = "我是问答路由"

        }

        tabBean3.apply {

            title = "体系"
            defaultNormal = R.drawable.ic_bottom_bar_navi
            defaultSelect = R.drawable.ic_bottom_bar_navi
            iconNormal = ""
            iconSelect = ""
            router = "我是体系路由"

        }

        tabBean4.apply {

            title = "我的"
            defaultNormal = R.drawable.ic_bottom_bar_mine
            defaultSelect = R.drawable.ic_bottom_bar_mine
            iconNormal = ""
            iconSelect = ""
            router = "我是我的路由"

        }

        var list = ArrayList<TabBean>()
        list.apply {
            add(tabBean1)
            add(tabBean2)
            add(tabBean3)
            add(tabBean4)
        }

        tab_wan.apply {

            setTitles(mTitles)
            setNormalIcons(mNormalIcons)
            setSelectedIcons(mSelectIcons)

            initFromAttribute()
            setupWithViewPager(vpWan)
        }

        tab_wan.setTabListener(object : OnTabSelectListener{
            override fun onTabSelect(index: Int) {

                vpWan.currentItem = index

            }

            override fun onInterruptSelect(index: Int): Boolean {
                return false
            }

        })
        showSuccess()
    }
}