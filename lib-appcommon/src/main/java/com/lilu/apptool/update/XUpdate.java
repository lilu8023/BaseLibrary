/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lilu.apptool.update;

import android.content.Context;

import androidx.annotation.NonNull;


import com.lilu.apptool.update.entity.DownloadEntity;
import com.lilu.apptool.update.entity.UpdateError;
import com.lilu.apptool.update.listener.OnInstallListener;
import com.lilu.apptool.update.listener.OnUpdateFailureListener;
import com.lilu.apptool.update.listener.impl.DefaultInstallListener;
import com.lilu.apptool.update.listener.impl.DefaultUpdateFailureListener;
import com.lilu.apptool.update.proxy.IUpdateChecker;
import com.lilu.apptool.update.proxy.IUpdateDownloader;
import com.lilu.apptool.update.proxy.IUpdateHttpService;
import com.lilu.apptool.update.proxy.IUpdateParser;
import com.lilu.apptool.update.proxy.IUpdatePrompter;
import com.lilu.apptool.update.proxy.impl.DefaultFileEncryptor;

import java.io.File;
import java.util.Map;


/**
 * 内部版本更新参数的获取
 *
 * @author xuexiang
 * @since 2018/7/10 下午4:27
 */
public final class XUpdate {

    /**
     * 标志当前更新提示是否已显示
     */
    private static boolean sIsShowUpdatePrompter = false;

    public static void setIsShowUpdatePrompter(boolean isShowUpdatePrompter) {
        XUpdate.sIsShowUpdatePrompter = isShowUpdatePrompter;
    }

    public static boolean isShowUpdatePrompter() {
        return XUpdate.sIsShowUpdatePrompter;
    }

    //===========================属性设置===================================//


    public static IUpdateHttpService getIUpdateHttpService() {
        return Update.getInstance().mIUpdateHttpService;
    }

    public static IUpdateChecker getIUpdateChecker() {
        return Update.getInstance().mIUpdateChecker;
    }

    public static IUpdateParser getIUpdateParser() {
        return Update.getInstance().mIUpdateParser;
    }

    public static IUpdatePrompter getIUpdatePrompter() {
        return Update.getInstance().mIUpdatePrompter;
    }

    public static IUpdateDownloader getIUpdateDownLoader() {
        return Update.getInstance().mIUpdateDownloader;
    }


    public static boolean isWifiOnly() {
        return Update.getInstance().mIsWifiOnly;
    }


    public static String getApkCacheDir() {
        return Update.getInstance().mApkCacheDir;
    }

    //===========================文件加密===================================//

    /**
     * 加密文件
     *
     * @param file 需要加密的文件
     */
    public static String encryptFile(File file) {
        if (Update.getInstance().mIFileEncryptor == null) {
            Update.getInstance().mIFileEncryptor = new DefaultFileEncryptor();
        }
        return Update.getInstance().mIFileEncryptor.encryptFile(file);
    }

    /**
     * 验证文件是否有效（加密是否一致）
     *
     * @param encrypt 加密值，不能为空
     * @param file    需要校验的文件
     * @return 文件是否有效
     */
    public static boolean isFileValid(String encrypt, File file) {
        if (Update.getInstance().mIFileEncryptor == null) {
            Update.getInstance().mIFileEncryptor = new DefaultFileEncryptor();
        }
        return Update.getInstance().mIFileEncryptor.isFileValid(encrypt, file);
    }

    //===========================apk安装监听===================================//

    public static OnInstallListener getOnInstallListener() {
        return Update.getInstance().mOnInstallListener;
    }

    /**
     * 开始安装apk文件
     *
     * @param context 传activity可以获取安装的返回值
     * @param apkFile apk文件
     */
    public static void startInstallApk(@NonNull Context context, @NonNull File apkFile) {
        startInstallApk(context, apkFile, new DownloadEntity());
    }

    /**
     * 开始安装apk文件
     *
     * @param context        传activity可以获取安装的返回值
     * @param apkFile        apk文件
     * @param downloadEntity 文件下载信息
     */
    public static void startInstallApk(@NonNull Context context, @NonNull File apkFile, @NonNull DownloadEntity downloadEntity) {
        if (onInstallApk(context, apkFile, downloadEntity)) {
            onApkInstallSuccess(); //静默安装的话，不会回调到这里
        } else {
            onUpdateError(UpdateError.ERROR.INSTALL_FAILED);
        }
    }

    /**
     * 安装apk
     *
     * @param context        传activity可以获取安装的返回值
     * @param apkFile        apk文件
     * @param downloadEntity 文件下载信息
     */
    private static boolean onInstallApk(Context context, File apkFile, DownloadEntity downloadEntity) {
        if (Update.getInstance().mOnInstallListener == null) {
            Update.getInstance().mOnInstallListener = new DefaultInstallListener();
        }
        return Update.getInstance().mOnInstallListener.onInstallApk(context, apkFile, downloadEntity);
    }

    /**
     * apk安装完毕
     */
    private static void onApkInstallSuccess() {
        if (Update.getInstance().mOnInstallListener == null) {
            Update.getInstance().mOnInstallListener = new DefaultInstallListener();
        }
        Update.getInstance().mOnInstallListener.onInstallApkSuccess();
    }

    //===========================更新出错===================================//

    public static OnUpdateFailureListener getOnUpdateFailureListener() {
        return Update.getInstance().mOnUpdateFailureListener;
    }

    /**
     * 更新出现错误
     *
     * @param errorCode
     */
    public static void onUpdateError(int errorCode) {
        onUpdateError(new UpdateError(errorCode));
    }

    /**
     * 更新出现错误
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static void onUpdateError(int errorCode, String message) {
        onUpdateError(new UpdateError(errorCode, message));
    }

    /**
     * 更新出现错误
     *
     * @param updateError
     */
    public static void onUpdateError(@NonNull UpdateError updateError) {
        if (Update.getInstance().mOnUpdateFailureListener == null) {
            Update.getInstance().mOnUpdateFailureListener = new DefaultUpdateFailureListener();
        }
        Update.getInstance().mOnUpdateFailureListener.onFailure(updateError);
    }

}
