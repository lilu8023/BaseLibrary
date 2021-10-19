package com.wan.main.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.ViewStub
import android.widget.Button
import com.lilu.appcommon.fragment.BaseFragment
import com.wan.main.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import org.reactivestreams.Subscriber


/**
 * Description:
 * @author lilu0916 on 2021/7/12
 *  No one knows this better than me
 */
class AnimationFragment : BaseFragment(), View.OnClickListener {

    private lateinit var bt1: Button
    private lateinit var bt2:Button

    private lateinit var bt_1:Button
    private lateinit var bt_2:Button

    private lateinit var stub1: ViewStub
    private lateinit var stub2: ViewStub

    var view1:View ?= null
    var view2:View ?= null

    override fun onVisibleFirst() {

    }

    override fun getRootView(): Int {
        return R.layout.fragment_animation
    }

    override fun init(rootView: View) {

        bt1 = rootView.findViewById(R.id.bt_show1)
        bt2 = rootView.findViewById(R.id.bt_show2)
        stub1 = rootView.findViewById(R.id.stub1)
        stub2 = rootView.findViewById(R.id.stub2)

        view1 = stub1.inflate()
        bt_1 = view1?.findViewById(R.id.bt_layout)!!

        view2 = stub2.inflate()

        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_show1->{

                    ObjectAnimator.ofFloat(view1,"translationX",-1080f, 0f).apply {
                        duration = 500
                    }.start()

                    ObjectAnimator.ofFloat(view2,"translationX",0f,1080f).apply {
                        duration = 500

                    }.start()



            }
            R.id.bt_show2->{
                if(view2?.translationX!! != 0f){
                    ObjectAnimator.ofFloat(view2,"translationX",1080f, 0f).apply {
                        duration = 200
                    }.start()

                    ObjectAnimator.ofFloat(view1,"translationX",0f,-1080f).apply {
                        duration = 200

                    }.start()

                }

            }
        }
    }


    private fun showView(v:View){

            ObjectAnimator.ofFloat(v,"translationX",0f, 1080f).apply {
            duration = 1000
        }.start()

    }


    private fun hideView(v:View){

        ObjectAnimator.ofFloat(view2,"translationX",1080f,0f).apply {
            duration = 1000

        }.start()

    }



}