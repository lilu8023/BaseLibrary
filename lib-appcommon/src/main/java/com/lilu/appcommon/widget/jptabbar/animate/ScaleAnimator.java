package com.lilu.appcommon.widget.jptabbar.animate;

import android.util.Log;
import android.view.View;


/**
 * Author jpeng
 * Date: 16-11-14
 * E-mail:peng8350@gmail.com
 * 实现图标缩放动画者
 */
public class ScaleAnimator implements Animatable{

    @Override
    public void onPressDown(View v, boolean selected) {
        Log.i("lilu","点击");
    }

    @Override
    public void onTouchOut(View v, boolean selected) {
    }

    @Override
    public void onSelectChanged(View v,boolean selected) {
    }

    @Override
    public void onPageAnimate(View v,float offset){
    }

    @Override
    public boolean isNeedPageAnimate() {
        return true;
    }

}