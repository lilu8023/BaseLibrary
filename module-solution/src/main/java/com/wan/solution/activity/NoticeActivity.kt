package com.wan.solution.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lilu.appcommon.activity.BaseActivity
import com.wan.solution.R
import com.wan.solution.router.SolutionRouterPath

/**
 * Description:
 * 消息页面
 * @author lilu0916 on 2021/11/10
 *  No one knows this better than me
 */
@Route(path = SolutionRouterPath.ACTIVITY_NOTICE,extras = 1)
class NoticeActivity : BaseActivity() {

    override fun getContentView() = R.layout.activity_notice

    override fun init(savedInstanceState: Bundle?) {

    }
}