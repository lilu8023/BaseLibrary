package com.lilu.appcommon.app;

import android.app.Application;

import com.lilu.appcommon.widget.statuslayout.callback.EmptyCallback;
import com.lilu.appcommon.widget.statuslayout.callback.ErrorCallback;
import com.lilu.appcommon.widget.statuslayout.callback.LoadingCallback;
import com.lilu.appcommon.widget.statuslayout.callback.SuccessCallback;
import com.lilu.appcommon.widget.statuslayout.core.LoadSir;

/**
 * Description:
 *
 * @author lilu0916 on 2021/8/17
 * No one knows this better than me
 */
public class Config {

    public static void initConfig(Application application){

        initStatusLayout();
    }

    private static void initStatusLayout(){

        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .setDefaultCallback(SuccessCallback.class)
        .commit();

    }
}
