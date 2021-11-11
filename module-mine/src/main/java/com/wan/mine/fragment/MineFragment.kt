package com.wan.mine.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.lilu.appcommon.fragment.BaseFragment
import com.lilu.apptool.router.RouterPath
import com.orhanobut.logger.Logger
import com.wan.mine.R

/**
 * Description:
 * @author lilu0916 on 2021/11/5
 *  No one knows this better than me
 */
@Route(path = RouterPath.FRAGMENT_MINE)
class MineFragment :BaseFragment(){


    override fun onVisibleFirst() {

        Logger.i("个人中心首次加载")
        showSuccess()
    }

    override fun getRootView(): Int {
        return R.layout.fragment_mine
    }

    override fun init(rootView: View) {

    }
}