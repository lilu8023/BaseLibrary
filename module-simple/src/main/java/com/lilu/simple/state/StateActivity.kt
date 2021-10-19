package com.lilu.simple.state

import android.animation.ValueAnimator
import android.os.Bundle
import com.lilu.appcommon.activity.BaseActivity
import com.lilu.simple.R

/**
 * Description:
 * @author lilu0916 on 2021/6/10 11:03
 *  No one knows this better than me
 */
class StateActivity : BaseActivity() {

    override fun init(savedInstanceState: Bundle?) {

        var amin = ValueAnimator.ofInt(0,3)
        amin.duration = 3000
        amin.start()
    }

    override fun getContentView(): Int {
        return R.layout.activity_base
    }
}