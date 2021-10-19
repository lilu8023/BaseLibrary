package com.lilu.simple.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Description:
 * viewpager2中的适配器
 * @author lilu0916 on 2021/6/11 11:19
 *  No one knows this better than me
 */
class LabFragmentAdapter(activity:FragmentActivity) : FragmentStateAdapter(activity) {

    private lateinit var mList: MutableList<Fragment>

    constructor(activity:FragmentActivity,list: MutableList<Fragment>):this(activity){
        this.mList = list

    }

    override fun getItemCount(): Int {
        return mList?.size
    }

    override fun createFragment(position: Int): Fragment {
        return mList[position]
    }
}