package com.lilu.apptool.router.arouter.callback;

import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Description:
 * 阿里路由跳转回调监听
 * @author lilu0916 on 2021/5/19 13:43
 * No one knows this better than me
 */
public class AliCallback implements NavigationCallback {

    @Override
    public void onFound(Postcard postcard) {
        Log.i("lilu","onFound");
    }

    @Override
    public void onLost(Postcard postcard) {
        Log.i("lilu","onLost");
    }

    @Override
    public void onArrival(Postcard postcard) {
        Log.i("lilu","onArrival");
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        Log.i("lilu","onInterrupt");
    }
}
