package com.lilu.appcommon.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lilu.appcommon.BuildConfig;
import com.lilu.apptool.imageloader.ImageLoader;
import com.lilu.apptool.imageloader.glide.GlideImageLoader;
import com.lilu.apptool.permission.Permission;
import com.lilu.apptool.permission.xxpermission.XXPermission;
import com.lilu.apptool.router.Router;
import com.lilu.apptool.router.arouter.AliRouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/18 15:12
 * No one knows this better than me
 */
public class BaseApplication extends Application {

    /**
     * 组件中application数组
     */
    private static final String[] MODULES_APPLICATION = {"com.wan.main.app.MainApplication"};

    @Override
    public void onCreate() {
        super.onCreate();


        //设置图片加载工具
        ImageLoader.getInstance().setImageLoader(this,new GlideImageLoader());

        //设置权限请求工具
        Permission.getInstance().setPermission(new XXPermission());

        Router.getInstance().setRouter(this,new AliRouter());

        //初始化组件中的application
        initModulesApplication();

        //初始化日志打印工具
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                //是否显示打印的线程
                .showThreadInfo(true)
                //显示的打印方法数
                .methodCount(2)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                //根据开关控制是否需要打印
                return BuildConfig.IS_DEBUG;
            }
        });

        Config.initConfig(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }

    /**
     * 反射模式初始化组件中的application
     */
    private void initModulesApplication(){
        for (String moduleImpl : MODULES_APPLICATION){
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IApplication){
                    ((IApplication) obj).init(this);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
