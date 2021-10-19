package com.lilu.apptool.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/11 10:30
 * No one knows this better than me
 */
public interface IImageLoader {

    void init(Context context);


    void displayImage(Context context, int resId, ImageView imageView);

    void displayImage(Context context, String url, ImageView imageView);

    void displayImage(Context context, String url, int defRes, int defErr, ImageView imageView);
}
