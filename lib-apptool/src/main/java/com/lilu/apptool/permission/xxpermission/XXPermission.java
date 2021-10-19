package com.lilu.apptool.permission.xxpermission;

import android.content.Context;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.lilu.apptool.permission.IPermission;
import com.lilu.apptool.permission.IPermissionCallback;

import java.util.List;

/**
 * Description:
 * XXPermission权限工具
 * @author lilu0916 on 2021/5/18 16:39
 * No one knows this better than me
 */
public class XXPermission implements IPermission {
    @Override
    public void init() {
        //设置拦截器
        XXPermissions.setInterceptor(new PermissionInterceptor() );
    }

    /**
     * 申请 一个权限
     * @param context   上下文
     * @param permission    权限名字
     * @param callback  回调
     */
    @Override
    public void permission(Context context,String permission, IPermissionCallback callback) {
        XXPermissions.with(context)
                .permission(permission)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if(callback != null){
                            if(all){
                                callback.granted();
                            }
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {

//                        XXPermissions.startPermissionActivity(context,permission);

                        if(callback != null){
                            callback.denied();
                        }
                    }
                });
    }
}
