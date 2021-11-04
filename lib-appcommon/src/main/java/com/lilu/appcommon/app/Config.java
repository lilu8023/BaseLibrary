package com.lilu.appcommon.app;

import android.app.Application;

import com.lilu.appcommon.R;
import com.lilu.appcommon.widget.statuslayout.UiStatusManager;
import com.lilu.appcommon.widget.statuslayout.annotation.UiStatus;
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



        UiStatusManager.getInstance()
                .addUiStatusConfig(UiStatus.LOADING, R.layout.layout_loading)
                .addUiStatusConfig(UiStatus.LOAD_ERROR,R.layout.layout_error)
                .addUiStatusConfig(UiStatus.EMPTY,R.layout.layout_empty);
    }
}
