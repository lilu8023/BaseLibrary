package com.lilu.apptool.permission;

import android.content.Context;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/18 16:34
 * No one knows this better than me
 */
public interface IPermission {

    void init();

    void permission(Context context,String permission, IPermissionCallback callback);
}
