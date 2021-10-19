package com.lilu.appcommon.widget.jptabbar.animate;

import android.view.View;

/**
 * Author jpeng
 * Date: 16-11-15
 * E-mail:peng8350@gmail.com
 * 实现跳跃图标的动画类
 */
public class JumpAnimater implements Animatable {

    @Override
    public void onPressDown(View v, boolean selected) {
    }

    @Override
    public void onTouchOut(View v, boolean selected) {
    }

    @Override
    public void onSelectChanged(View v, boolean selected) {
    }

    @Override
    public void onPageAnimate(View v, float offset) {
    }

    @Override
    public boolean isNeedPageAnimate() {
        return true;
    }

}
