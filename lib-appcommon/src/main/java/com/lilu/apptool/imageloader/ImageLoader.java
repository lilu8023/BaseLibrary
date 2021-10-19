package com.lilu.apptool.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/11 10:31
 * No one knows this better than me
 */
public class ImageLoader implements IImageLoader {

    private volatile static ImageLoader instance;
    private IImageLoader imageLoader;

    private ImageLoader(){
    }

    public static ImageLoader getInstance(){
        if(instance == null){
            synchronized (ImageLoader.class){
                if(instance == null){
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public void setImageLoader(Context context,IImageLoader loader){
        if(this.imageLoader != loader){
            this.imageLoader = loader;
            if(this.imageLoader != null){
                this.imageLoader.init(context);
            }
        }
    }

    @Override
    public void init(Context context) {
        if(imageLoader != null){
            imageLoader.init(context);
        }
    }

    @Override
    public void displayImage(Context context, int resId, ImageView imageView) {
        if(imageLoader != null){
            imageLoader.displayImage(context,resId,imageView);
        }
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        if(imageLoader != null){
            imageLoader.displayImage(context,url,imageView);
        }
    }

    @Override
    public void displayImage(Context context, String url, int defRes, int defErr, ImageView imageView) {
        if(imageLoader != null){
            imageLoader.displayImage(context,url,defRes,defErr,imageView);
        }
    }
}
