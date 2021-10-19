package com.lilu.simple.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Description:
 * @author lilu0916 on 2021/6/10 11:36
 *  No one knows this better than me
 */
class SimpleAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    lateinit var mList:List<Fragment>
    lateinit var mFm:FragmentManager
    lateinit var titleList:List<String>

    constructor(fm: FragmentManager, behavior: Int,list:List<Fragment>) : this(fm, behavior) {
        this.mList = list
        this.mFm = fm
    }


    override fun getCount(): Int {

        return mList.size
    }

    override fun getItem(position: Int): Fragment {

        return mList[position]

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


}