package com.wan.main.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lilu.appcommon.activity.BaseActivity
import com.wan.main.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Description:
 * @author lilu0916 on 2021/6/11
 *  No one knows this better than me
 */
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Observable.timer(3,TimeUnit.SECONDS)
                .subscribe(object :Observer<Long>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Long) {


                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {

                        startActivity(Intent(this@SplashActivity,WanMainActivity::class.java))

                    }
                })
    }




}