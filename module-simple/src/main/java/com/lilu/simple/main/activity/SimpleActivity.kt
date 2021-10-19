package com.lilu.simple.main.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lilu.simple.R
import com.lilu.simple.main.adapter.SimpleAdapter
import com.lilu.simple.main.fragment.HelperFragment
import com.lilu.simple.main.fragment.LabFragment
import com.lilu.simple.main.fragment.SimpleFragment
import java.util.*

/**
 * Description:
 * @author lilu0916 on 2021/6/10 11:10
 *  No one knows this better than me
 */
class SimpleActivity :AppCompatActivity(){


    lateinit var vpSimple: ViewPager
    lateinit var tlSimple: TabLayout
    lateinit var vpAdapter: SimpleAdapter
    private lateinit var vpList:MutableList<Fragment>
    lateinit var titleList:MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_simple)

        vpSimple = findViewById(R.id.vp_simple)
        tlSimple = findViewById(R.id.tl_simple)

        vpList = ArrayList()

        vpList.add(SimpleFragment())
        vpList.add(HelperFragment())
        vpList.add(LabFragment())

        titleList = ArrayList()
        titleList.add("首页")
        titleList.add("Helper")
        titleList.add("Lab")

        vpSimple.offscreenPageLimit = 3
        vpAdapter = SimpleAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,vpList)
        vpAdapter.titleList = titleList
        vpSimple.adapter = vpAdapter
        tlSimple.setupWithViewPager(vpSimple)
    }

}