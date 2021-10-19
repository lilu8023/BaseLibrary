package com.lilu.simple.main.fragment

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.appcommon.widget.recyclerview.GridSpaceItemDecoration
import com.lilu.simple.R
import com.lilu.simple.main.adapter.SimpleRvAdapter
import java.util.*

/**
 * Description:
 * @author lilu0916 on 2021/6/10 11:15
 *  No one knows this better than me
 */
class SimpleFragment :BaseFragment() {

    private lateinit var rvSimple:RecyclerView
    lateinit var mList:MutableList<String>
    lateinit var mAdapter:SimpleRvAdapter

    override fun onVisibleFirst() {
        Log.i("lilu","SimpleFragment - onVisibleFirst");
    }

    override fun getRootView(): Int {
        return R.layout.fragment_simple
    }

    override fun init(rootView: View) {

        rvSimple = rootView.findViewById(R.id.rv_simple)

        mList = ArrayList()

        for (i in 0..10){
            mList.add("选项$i")
        }
        mAdapter = SimpleRvAdapter(mList)

        var rvManager = GridSpaceItemDecoration(10)
        rvManager.setEndFromSize(0)
        rvSimple.addItemDecoration(rvManager)
        rvSimple.layoutManager = GridLayoutManager(activity,3)
        rvSimple.adapter = mAdapter
    }
}