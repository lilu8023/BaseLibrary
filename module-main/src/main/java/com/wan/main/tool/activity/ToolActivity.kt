package com.wan.main.tool.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lilu.appcommon.activity.BaseActivity
import com.wan.main.R
import com.wan.main.router.MainRouterPath
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Description:
 * 常用工具页
 * @author lilu0916 on 2021/11/3
 *  No one knows this better than me
 */
@Route(path = MainRouterPath.ACTIVITY_TOOL)
class ToolActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_tool
    }

    override fun init(savedInstanceState: Bundle?) {


        Observable.intervalRange(0,4, 1,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Long) {
                        //设置倒计时的值
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        showSuccess()
                    }
                })

    }
}