package com.lilu.apptool.router.arouter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lilu.apptool.router.IRouter;
import com.lilu.apptool.router.arouter.callback.AliCallback;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/19 11:13
 * No one knows this better than me
 */
public class AliRouter implements IRouter {


    @Override
    public void init(Application application) {

        ARouter.openLog();
        ARouter.openDebug();

        ARouter.init(application);
    }

    /**
     * 路由打开界面 路由是本地规定好的路由
     * @param path 本地路由
     */
    @Override
    public void startActivity(String path) {
        ARouter.getInstance()
                .build(path)
                .navigation();
    }

    @Override
    public void startActivity(String path, Bundle bundle) {
        ARouter.getInstance()
                .build(path)
                .with(bundle)
                .navigation();
    }

    @Override
    public void startActivity(Activity activity, String path, int requestCode) {
        ARouter.getInstance()
                .build(path)
                .navigation(activity,requestCode);
    }

    @Override
    public void startActivity(Activity activity, String path, Bundle bundle, int requestCode) {
        ARouter.getInstance()
                .build(path)
                .with(bundle)
                .navigation(activity,requestCode);
    }

    @Override
    public void startActivityByUrl(Context context,String url) {
        //验证一下url是否合法
        Uri uri = Uri.parse(url);
        if(uri != null){
            ARouter.getInstance()
                    .build(uri)
                    .greenChannel()
                    .navigation(context,new AliCallback());
        }
    }

    @Override
    public void startActivityByUrl(Activity activity, String url, int requestCode) {
        //验证一下url是否合法
        Uri uri = Uri.parse(url);
        if(uri != null){
            ARouter.getInstance()
                    .build(uri)
                    .greenChannel()
                    .navigation(activity,requestCode,new AliCallback());
        }
    }


}
