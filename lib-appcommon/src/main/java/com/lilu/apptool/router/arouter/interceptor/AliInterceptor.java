package com.lilu.apptool.router.arouter.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.orhanobut.logger.Logger;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/19 13:44
 * No one knows this better than me
 */
@Interceptor(priority = 10,name = "UserCheck")
public class AliInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if(postcard.getExtra() == 1){
            Logger.i("需要登录");
        }else{
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}
