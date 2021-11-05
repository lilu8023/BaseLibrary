package com.wan.system.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wan.system.fragment.NavigationFragment
import com.wan.system.fragment.TreeFragment

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class SystemAdapter(context:Fragment,val tabs:Array<String>) :FragmentStateAdapter(context) {


    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TreeFragment()

            else ->  NavigationFragment()
        }
    }
}