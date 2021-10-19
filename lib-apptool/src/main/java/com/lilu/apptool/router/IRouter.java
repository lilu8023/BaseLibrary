package com.lilu.apptool.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/19 11:08
 * No one knows this better than me
 */
public interface IRouter {

    void init(Application application);

    void startActivity(String path);
    void startActivity(String path, Bundle bundle);
    void startActivity(Activity activity,String path, int requestCode);
    void startActivity(Activity activity,String path, Bundle bundle,int requestCode);


    void startActivityByUrl(Context context, String url);
    void startActivityByUrl(Activity activity, String url,int requestCode);

}
