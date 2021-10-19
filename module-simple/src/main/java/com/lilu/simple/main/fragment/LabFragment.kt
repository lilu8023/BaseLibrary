package com.lilu.simple.main.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.simple.R
import com.lilu.simple.main.adapter.LabAdapter
import com.lilu.simple.main.adapter.LabFragmentAdapter
import java.util.*

/**
 * Description:
 * @author lilu0916 on 2021/6/10 11:19
 *  No one knows this better than me
 */
class LabFragment : BaseFragment() {

    private lateinit var labSimple: ViewPager2
    lateinit var tlSimple: TabLayout

    lateinit var labAdapter: LabAdapter
    lateinit var labFraAdapter : LabFragmentAdapter

    private lateinit var vpList:MutableList<Fragment>

    lateinit var titleList:MutableList<String>


    override fun onVisibleFirst() {
        Log.i("lilu","LabFragment - onVisibleFirst");
    }

    override fun onVisible() {
        super.onVisible()

        Log.i("lilu","LabFragment - onVisible");
    }

    override fun onPause() {
        super.onPause()
    }
    override fun onInvisible() {
        super.onInvisible()

        Log.i("lilu","LabFragment - onInvisible");
    }

    override fun getRootView(): Int {
        return R.layout.fragment_lab
    }

    override fun init(rootView: View) {

        labSimple = rootView.findViewById(R.id.vp_lab)
        tlSimple = rootView.findViewById(R.id.tl_lab)

        vpList = ArrayList()

        vpList.add(LabFragment1())
        vpList.add(LabFragment2())
        vpList.add(LabFragment3())

        titleList = ArrayList()
        titleList.add("首页")
        titleList.add("Helper")
        titleList.add("Lab")


//        labAdapter = LabAdapter(titleList)
//        labSimple.adapter = labAdapter
//        labSimple.orientation = ViewPager2.ORIENTATION_VERTICAL

        tlSimple.addTab(tlSimple.newTab().setText("首页"))
        tlSimple.addTab(tlSimple.newTab().setText("Helper"))
        tlSimple.addTab(tlSimple.newTab().setText("Lab"))

        labFraAdapter = activity?.let { LabFragmentAdapter(it,vpList) }!!
        labSimple.adapter = labFraAdapter
//        labSimple.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//
//                Log.i("lilu","选中了$position")
//                tlSimple.getTabAt(position)?.select()
//            }
//        })
//        tlSimple.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.let {
//                    labSimple.currentItem = it.position
//                }
//
//            }
//
//            override fun onTabUnselected(p0: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(p0: TabLayout.Tab?) {
//            }
//
//        })

        //viewpager2和tablayout进行绑定
        TabLayoutMediator(tlSimple,labSimple,true) { tab, position ->
            run {
                tab.text = titleList[position]
            }
        }.attach()
    }
}