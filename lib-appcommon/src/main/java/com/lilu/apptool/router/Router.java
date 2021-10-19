package com.lilu.apptool.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/18 15:46
 * No one knows this better than me
 */
public class Router implements IRouter{

    private static Router instance;
    private IRouter router;

    private Router() {
    }

    public static Router getInstance(){
        if(instance == null){
            synchronized (Router.class){
                if(instance == null){
                    instance = new Router();
                }
            }
        }
        return instance;
    }

    public void setRouter(Application application,IRouter route){
        if(router != route){
            router = route;
            init(application);
        }
    }

    @Override
    public void init(Application application) {
        if(router != null){
            router.init(application);
        }
    }

    @Override
    public void startActivity(String path){
        if(router != null){
            router.startActivity(path);
        }
    }

    @Override
    public void startActivity(String path, Bundle bundle) {

    }

    @Override
    public void startActivity(Activity activity, String path, int requestCode) {

    }

    @Override
    public void startActivity(Activity activity, String path, Bundle bundle, int requestCode) {

    }

    @Override
    public void startActivityByUrl(Context context, String url) {
        if(router != null){
            router.startActivityByUrl(context,url);
        }
    }

    @Override
    public void startActivityByUrl(Activity activity, String url, int requestCode) {

    }
}
