package com.lilu.apptool.permission;

import android.content.Context;

/**
 * Description:
 * 权限工具申请工具
 * @author lilu0916 on 2021/5/18 16:38
 * No one knows this better than me
 */
public class Permission implements IPermission {


    private static volatile Permission instance;
    private IPermission permission;

    private Permission() {
    }

    public static Permission getInstance(){
        if(instance == null){
            synchronized (Permission.class){
                if(instance == null){
                    instance = new Permission();
                }
            }
        }
        return instance;
    }

    public void setPermission(IPermission permit){
        if(this.permission != permit){
            this.permission = permit;
            if(this.permission != null){
                this.permission.init();
            }
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void permission(Context context,String permit, IPermissionCallback callback) {
        if(permission != null){
            permission.permission(context,permit,callback);
        }

    }
}
