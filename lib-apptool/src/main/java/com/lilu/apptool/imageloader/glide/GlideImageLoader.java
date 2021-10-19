package com.lilu.apptool.imageloader.glide;

import android.content.Context;
import android.widget.ImageView;

import com.lilu.apptool.imageloader.GlideApp;
import com.lilu.apptool.imageloader.IImageLoader;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/18 14:50
 * No one knows this better than me
 */
public class GlideImageLoader implements IImageLoader {

    //统一的加载占位图
    public static final int DEFAULT_RES = 0;
    //统一的错误占位图
    public static final int ERROR_RES = 0;

    @Override
    public void init(Context context) {

    }

    private void testAvailable() {
        if(DEFAULT_RES == 0 || ERROR_RES == 0) {
            throw new ExceptionInInitializerError("必须设置默认占位图");
        }
    }

    /**
     * 加载资源图片
     * @param context 上下文
     * @param resId 资源编号
     * @param imageView 图片控件
     */
    @Override
    public void displayImage(Context context, int resId, ImageView imageView) {
        GlideApp.with(context)
                .load(resId)
                .into(imageView);
    }

    /**
     * 加载网络图片
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片控件
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        testAvailable();
        GlideApp.with(context)
                .load(url)
                .placeholder(DEFAULT_RES)
                .error(ERROR_RES)
                .into(imageView);

    }

    /**
     * 加载网络图片
     * @param context   上下文
     * @param url   图片地址
     * @param defRes    加载占位图
     * @param defErr    加载错误图
     * @param imageView 图片控件
     */
    @Override
    public void displayImage(Context context, String url, int defRes, int defErr, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(defRes)
                .error(defErr)
                .into(imageView);
    }
}
