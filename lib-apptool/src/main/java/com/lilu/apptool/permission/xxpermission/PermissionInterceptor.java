package com.lilu.apptool.permission.xxpermission;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.hjq.permissions.IPermissionInterceptor;
import com.hjq.permissions.OnPermissionCallback;
import com.lilu.apptool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * XXPermission权限工具拦截器
 * @author lilu0916 on 2021/5/19 9:57
 * No one knows this better than me
 */
public class PermissionInterceptor implements IPermissionInterceptor {

    @Override
    public void requestPermissions(FragmentActivity activity, OnPermissionCallback callback, List<String> permissions) {

        Log.i("lilu","申请权限了:"+getPermissionHint(activity,permissions));
        IPermissionInterceptor.super.requestPermissions(activity,callback,permissions);

    }


    /**
     * 根据权限获取提示
     */
    protected String getPermissionHint(Context context, List<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return context.getString(R.string.common_permission_fail_2);
        }

        List<String> hints = new ArrayList<>();
        for (String permission : permissions) {
            switch (permission) {
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                case Manifest.permission.MANAGE_EXTERNAL_STORAGE: {
                    String hint = context.getString(R.string.common_permission_storage);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.CAMERA: {
                    String hint = context.getString(R.string.common_permission_camera);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.RECORD_AUDIO: {
                    String hint = context.getString(R.string.common_permission_microphone);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                case Manifest.permission.ACCESS_BACKGROUND_LOCATION: {
                    String hint;
                    if (!permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) &&
                            !permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        hint = context.getString(R.string.common_permission_location_background);
                    } else {
                        hint = context.getString(R.string.common_permission_location);
                    }
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.READ_PHONE_STATE:
                case Manifest.permission.CALL_PHONE:
                case Manifest.permission.ADD_VOICEMAIL:
                case Manifest.permission.USE_SIP:
                case Manifest.permission.READ_PHONE_NUMBERS:
                case Manifest.permission.ANSWER_PHONE_CALLS: {
                    String hint = context.getString(R.string.common_permission_phone);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.GET_ACCOUNTS:
                case Manifest.permission.READ_CONTACTS:
                case Manifest.permission.WRITE_CONTACTS: {
                    String hint = context.getString(R.string.common_permission_contacts);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.READ_CALENDAR:
                case Manifest.permission.WRITE_CALENDAR: {
                    String hint = context.getString(R.string.common_permission_calendar);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.READ_CALL_LOG:
                case Manifest.permission.WRITE_CALL_LOG:
                case Manifest.permission.PROCESS_OUTGOING_CALLS: {
                    String hint = context.getString(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ?
                            R.string.common_permission_call_log : R.string.common_permission_phone);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.BODY_SENSORS: {
                    String hint = context.getString(R.string.common_permission_sensors);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.ACTIVITY_RECOGNITION: {
                    String hint = context.getString(R.string.common_permission_activity_recognition);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.SEND_SMS:
                case Manifest.permission.RECEIVE_SMS:
                case Manifest.permission.READ_SMS:
                case Manifest.permission.RECEIVE_WAP_PUSH:
                case Manifest.permission.RECEIVE_MMS: {
                    String hint = context.getString(R.string.common_permission_sms);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.REQUEST_INSTALL_PACKAGES: {
                    String hint = context.getString(R.string.common_permission_install);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
//                case Manifest.permission.: {
//                    String hint = context.getString(R.string.common_permission_notification);
//                    if (!hints.contains(hint)) {
//                        hints.add(hint);
//                    }
//                    break;
//                }
                case Manifest.permission.SYSTEM_ALERT_WINDOW: {
                    String hint = context.getString(R.string.common_permission_window);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Manifest.permission.WRITE_SETTINGS: {
                    String hint = context.getString(R.string.common_permission_setting);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        if (!hints.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String text : hints) {
                if (builder.length() == 0) {
                    builder.append(text);
                } else {
                    builder.append("、")
                            .append(text);
                }
            }
            builder.append(" ");
            return context.getString(R.string.common_permission_message_1, builder.toString());
        }

        return context.getString(R.string.common_permission_fail_2);
    }
}
