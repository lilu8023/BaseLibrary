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

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.lilu.apptool.update.entity.UpdateError;
import com.lilu.apptool.update.listener.OnInstallListener;
import com.lilu.apptool.update.listener.OnUpdateFailureListener;
import com.lilu.apptool.update.listener.impl.DefaultInstallListener;
import com.lilu.apptool.update.listener.impl.DefaultUpdateFailureListener;
import com.lilu.apptool.update.proxy.IFileEncryptor;
import com.lilu.apptool.update.proxy.IUpdateChecker;
import com.lilu.apptool.update.proxy.IUpdateDownloader;
import com.lilu.apptool.update.proxy.IUpdateHttpService;
import com.lilu.apptool.update.proxy.IUpdateParser;
import com.lilu.apptool.update.proxy.IUpdatePrompter;
import com.lilu.apptool.update.proxy.impl.DefaultFileEncryptor;
import com.lilu.apptool.update.proxy.impl.DefaultUpdateChecker;
import com.lilu.apptool.update.proxy.impl.DefaultUpdateDownloader;
import com.lilu.apptool.update.proxy.impl.DefaultUpdateParser;
import com.lilu.apptool.update.proxy.impl.DefaultUpdatePrompter;
import com.lilu.apptool.update.utils.ApkInstallUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 版本更新的入口
 *
 * @author xuexiang
 * @since 2018/6/29 下午7:47
 */
public class Update {

    private Application mContext;
    private static Update sInstance;

    //========全局属性==========//
    /**
     * 是否只在wifi下进行版本更新检查
     */
    boolean mIsWifiOnly;
    /**
     * 下载的apk文件缓存目录
     */
    String mApkCacheDir;
    //========全局更新实现接口==========//
    /**
     * 版本更新网络请求服务API
     */
    IUpdateHttpService mIUpdateHttpService;
    /**
     * 版本更新检查器【有默认】
     */
    IUpdateChecker mIUpdateChecker;
    /**
     * 版本更新解析器【有默认】
     */
    IUpdateParser mIUpdateParser;
    /**
     * 版本更新提示器【有默认】
     */
    IUpdatePrompter mIUpdatePrompter;
    /**
     * 版本更新下载器【有默认】
     */
    IUpdateDownloader mIUpdateDownloader;
    /**
     * 文件加密器【有默认】
     */
    IFileEncryptor mIFileEncryptor;
    /**
     * APK安装监听【有默认】
     */
    OnInstallListener mOnInstallListener;
    /**
     * 更新出错监听【有默认】
     */
    OnUpdateFailureListener mOnUpdateFailureListener;

    //===========================初始化===================================//

    private Update() {
        mIsWifiOnly = true;

        mIUpdateChecker = new DefaultUpdateChecker();
        mIUpdateParser = new DefaultUpdateParser();
        mIUpdateDownloader = new DefaultUpdateDownloader();
        mIUpdatePrompter = new DefaultUpdatePrompter();
        mIFileEncryptor = new DefaultFileEncryptor();
        mOnInstallListener = new DefaultInstallListener();
        mOnUpdateFailureListener = new DefaultUpdateFailureListener();
    }

    /**
     * 获取版本更新的入口
     *
     * @return 版本更新的入口
     */
    public static Update getInstance() {
        if (sInstance == null) {
            synchronized (Update.class) {
                if (sInstance == null) {
                    sInstance = new Update();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化
     *
     * @param application
     */
    public void init(Application application) {
        mContext = application;
        UpdateError.init(mContext);
    }

    private Application getApplication() {
        testInitialize();
        return mContext;
    }

    private void testInitialize() {
        if (mContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 XUpdate.get().init() 初始化！");
        }
    }

    public static Context getContext() {
        return getInstance().getApplication();
    }

    //===========================对外版本更新api===================================//

    /**
     * 获取版本更新构建者
     *
     * @param context
     * @return
     */
    public static UpdateManager.Builder newBuild(@NonNull Context context) {
        return new UpdateManager.Builder(context);
    }


    //===========================属性设置===================================//


    /**
     * 设置全局版本更新网络请求服务API
     *
     * @param updateHttpService
     * @return
     */
    public Update setIUpdateHttpService(@NonNull IUpdateHttpService updateHttpService) {
        mIUpdateHttpService = updateHttpService;
        return this;
    }

    /**
     * 设置全局版本更新检查
     *
     * @param updateChecker 版本更新检查器
     * @return
     */
    public Update setIUpdateChecker(@NonNull IUpdateChecker updateChecker) {
        mIUpdateChecker = updateChecker;
        return this;
    }

    /**
     * 设置全局版本更新的解析器
     *
     * @param updateParser 版本更新的解析器
     * @return
     */
    public Update setIUpdateParser(@NonNull IUpdateParser updateParser) {
        mIUpdateParser = updateParser;
        return this;
    }

    /**
     * 设置全局版本更新提示器
     *
     * @param updatePrompter 版本更新提示器
     * @return
     */
    public Update setIUpdatePrompter(IUpdatePrompter updatePrompter) {
        mIUpdatePrompter = updatePrompter;
        return this;
    }

    /**
     * 设置全局版本更新下载器
     *
     * @param updateDownLoader 版本更新下载器
     * @return
     */
    public Update setIUpdateDownLoader(@NonNull IUpdateDownloader updateDownLoader) {
        mIUpdateDownloader = updateDownLoader;
        return this;
    }


    /**
     * 设置是否只在wifi下进行版本更新检查
     *
     * @param isWifiOnly
     * @return
     */
    public Update isWifiOnly(boolean isWifiOnly) {
        mIsWifiOnly = isWifiOnly;
        return this;
    }


    /**
     * 设置apk的缓存路径
     *
     * @param apkCacheDir
     * @return
     */
    public Update setApkCacheDir(String apkCacheDir) {
        mApkCacheDir = apkCacheDir;
        return this;
    }



    //===========================apk安装监听===================================//


    /**
     * 设置文件加密器
     *
     * @param fileEncryptor
     * @return
     */
    public Update setIFileEncryptor(IFileEncryptor fileEncryptor) {
        mIFileEncryptor = fileEncryptor;
        return this;
    }

    /**
     * 设置安装监听
     *
     * @param onInstallListener
     * @return
     */
    public Update setOnInstallListener(OnInstallListener onInstallListener) {
        mOnInstallListener = onInstallListener;
        return this;
    }

    //===========================更新出错===================================//

    /**
     * 设置更新出错的监听
     *
     * @param onUpdateFailureListener
     * @return
     */
    public Update setOnUpdateFailureListener(@NonNull OnUpdateFailureListener onUpdateFailureListener) {
        mOnUpdateFailureListener = onUpdateFailureListener;
        return this;
    }


}
