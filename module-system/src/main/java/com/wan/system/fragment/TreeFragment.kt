package com.wan.system.fragment

import android.view.View
import com.lilu.appcommon.fragment.BaseFragment
import com.wan.system.R
import com.wan.system.model.SystemModel

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
class TreeFragment :BaseFragment() {
    override fun onVisibleFirst() {
        showSuccess()
    }

    override fun getRootView(): Int {
        return R.layout.fragment_system
    }

    override fun init(rootView: View) {

        get(SystemModel::class.java)

    }
}