package com.lilu.appcommon.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.lilu.appcommon.R;
import com.lilu.appcommon.widget.statuslayout.UiStatusManager;
import com.lilu.appcommon.widget.statuslayout.annotation.UiStatus;
import com.lilu.appcommon.widget.statuslayout.callback.Loading;
import com.lilu.appcommon.widget.statuslayout.callback.ProgressCallback;
import com.lilu.appcommon.widget.statuslayout.core.LoadSir;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

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

        //初始化状态管理布局
        LoadSir.beginBuilder()
                .addCallback(new Loading())
                .setDefaultCallback(Loading.class)
                .commit();

        //初始化下拉刷新与上拉加载更多
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsHeader(context);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });

//        UiStatusManager.getInstance()
//                .addUiStatusConfig(UiStatus.LOADING, R.layout.layout_loading)
//                .addUiStatusConfig(UiStatus.LOAD_ERROR,R.layout.layout_error)
//                .addUiStatusConfig(UiStatus.EMPTY,R.layout.layout_empty);
    }
}
