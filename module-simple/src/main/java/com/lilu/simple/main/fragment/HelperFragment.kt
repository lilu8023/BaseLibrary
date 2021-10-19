package com.lilu.simple.main.fragment

import android.util.Log
import android.view.View
import android.widget.TextView
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.simple.R

/**
 * Description:
 * @author lilu0916 on 2021/6/10 11:18
 *  No one knows this better than me
 */
class HelperFragment : BaseFragment() {

    lateinit var tvSimple: TextView

    override fun onVisibleFirst() {
        Log.i("lilu","HelperFragment - onVisibleFirst");
    }

    override fun onVisible() {
        super.onVisible()

        Log.i("lilu","HelperFragment - onVisible");
    }

    override fun onInvisible() {
        super.onInvisible()

        Log.i("lilu","HelperFragment - onInvisible");
    }

    override fun getRootView(): Int {
        return R.layout.fragment_helper
    }

    override fun init(rootView: View) {

        tvSimple = rootView.findViewById(R.id.tv_simple)

        tvSimple.text = "Helper"
    }
}