package com.wan.main.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.lilu.apptool.router.Router
import com.lilu.apptool.router.RouterPath
import com.wan.main.R
import com.wan.main.databinding.ActivitySplashBinding
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Description:
 * APP的启动闪屏页
 * 1、不能集成base,需要优先处理隐私权限
 *
 * @author lilu0916 on 2021/6/11
 *  No one knows this better than me
 */
class SplashActivity : AppCompatActivity() {

    lateinit var mBinding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        /**
         * 启动三秒倒计时
         */
        Observable.intervalRange(0,4, 1,1,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Long) {
                        //设置倒计时的值
                        mBinding.tvSplashCount.text = "${3-t}"
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        //倒计时结束，跳转到首页并关闭此页面
                        Router.getInstance().startActivity(RouterPath.ACTIVITY_MAIN)
                        finish()
                    }
                })
    }




}