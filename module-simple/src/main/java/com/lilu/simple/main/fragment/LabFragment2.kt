package com.lilu.simple.main.fragment

import android.util.Log
import android.view.View
import android.widget.TextView
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.simple.R

/**
 * Description:
 * @author lilu0916 on 2021/6/10 11:19
 *  No one knows this better than me
 */
class LabFragment2 : BaseFragment() {

    lateinit var tvSimple: TextView

    override fun onVisibleFirst() {
        Log.i("lilu","LabFragment2 - onVisibleFirst");
    }

    override fun onVisible() {
        super.onVisible()

        Log.i("lilu","LabFragment2 - onVisible");
    }

    override fun onInvisible() {
        super.onInvisible()

        Log.i("lilu","LabFragment2 - onInvisible");
    }

    override fun getRootView(): Int {
        return R.layout.fragment_lab_child
    }

    override fun init(rootView: View) {

        tvSimple = rootView.findViewById(R.id.tv_lab_child)

        tvSimple.text = "LAB"
    }
}