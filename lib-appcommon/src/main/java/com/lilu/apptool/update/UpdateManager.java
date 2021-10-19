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
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import com.lilu.apptool.update.entity.PromptEntity;
import com.lilu.apptool.update.entity.UpdateEntity;
import com.lilu.apptool.update.entity.UpdateError;
import com.lilu.apptool.update.listener.IUpdateParseCallback;
import com.lilu.apptool.update.proxy.IUpdateChecker;
import com.lilu.apptool.update.proxy.IUpdateDownloader;
import com.lilu.apptool.update.proxy.IUpdateHttpService;
import com.lilu.apptool.update.proxy.IUpdateParser;
import com.lilu.apptool.update.proxy.IUpdatePrompter;
import com.lilu.apptool.update.proxy.IUpdateProxy;
import com.lilu.apptool.update.proxy.impl.DefaultUpdatePrompter;
import com.lilu.apptool.update.service.OnFileDownloadListener;
import com.lilu.apptool.update.utils.UpdateUtils;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.TreeMap;


/**
 * 版本更新管理者
 *
 * @author xuexiang
 * @since 2018/7/1 下午9:49
 */
public class UpdateManager implements IUpdateProxy {
    /**
     * 更新信息
     */
    private UpdateEntity mUpdateEntity;

    private WeakReference<Context> mContext;
    //============请求参数==============//
    /**
     * apk缓存的目录
     */
    private String mApkCacheDir;

    //===========更新模式================//
    /**
     * 是否只在wifi下进行版本更新检查
     */
    private boolean mIsWifiOnly;
    //===========更新组件===============//
    /**
     * 版本更新网络请求服务API
     */
    private IUpdateHttpService mIUpdateHttpService;
    /**
     * 版本更新检查器
     */
    private IUpdateChecker mIUpdateChecker;
    /**
     * 版本更新解析器
     */
    private IUpdateParser mIUpdateParser;
    /**
     * 版本更新下载器
     */
    private IUpdateDownloader mIUpdateDownloader;
    /**
     * 文件下载监听
     */
    private OnFileDownloadListener mOnFileDownloadListener;
    /**
     * 版本更新提示器
     */
    private IUpdatePrompter mIUpdatePrompter;
    /**
     * 版本更新提示器参数信息
     */
    private PromptEntity mPromptEntity;

    /**
     * 构造函数
     *
     * @param builder
     */
    private UpdateManager(Builder builder) {
        mContext = new WeakReference<>(builder.context);
        mApkCacheDir = builder.apkCacheDir;

        mIsWifiOnly = builder.isWifiOnly;

        mIUpdateHttpService = builder.updateHttpService;

        mIUpdateChecker = builder.updateChecker;
        mIUpdateParser = builder.updateParser;
        mIUpdateDownloader = builder.updateDownLoader;
        mOnFileDownloadListener = builder.onFileDownloadListener;

        mIUpdatePrompter = builder.updatePrompter;
        mPromptEntity = builder.promptEntity;
    }


    @Nullable
    @Override
    public Context getContext() {
        return mContext != null ? mContext.get() : null;
    }

    @Override
    public IUpdateHttpService getIUpdateHttpService() {
        return mIUpdateHttpService;
    }

    /**
     * 开始版本更新
     */
    @Override
    public void update() {
        checkVersion();
    }

    /**
     * 执行版本更新操作
     */
    private void doUpdate() {
        onBeforeCheck();

        if (mIsWifiOnly) {
            if (UpdateUtils.checkWifi()) {
                checkVersion();
            } else {
                onAfterCheck();
                XUpdate.onUpdateError(UpdateError.ERROR.CHECK_NO_WIFI);
            }
        } else {
            if (UpdateUtils.checkNetwork()) {
                checkVersion();
            } else {
                onAfterCheck();
                XUpdate.onUpdateError(UpdateError.ERROR.CHECK_NO_NETWORK);
            }
        }
    }

    /**
     * 版本检查之前
     */
    @Override
    public void onBeforeCheck() {
        mIUpdateChecker.onBeforeCheck();
    }

    /**
     * 执行网络请求，检查应用的版本信息
     */
    @Override
    public void checkVersion() {
        mIUpdateChecker.checkVersion(this);
    }

    /**
     * 版本检查之后
     */
    @Override
    public void onAfterCheck() {
        mIUpdateChecker.onAfterCheck();
    }

    /**
     * 将请求的json结果解析为版本更新信息实体
     *
     * @param json
     * @return
     */
    @Override
    public UpdateEntity parseJson(@NonNull String json) throws Exception {
        mUpdateEntity = mIUpdateParser.parseJson(json);
        mUpdateEntity = refreshParams(mUpdateEntity);
        return mUpdateEntity;
    }

    /**
     * 刷新本地参数
     *
     * @param updateEntity
     */
    private UpdateEntity refreshParams(UpdateEntity updateEntity) {
        //更新信息（本地信息）
        if (updateEntity != null) {
            updateEntity.setApkCacheDir(mApkCacheDir);
            updateEntity.setIUpdateHttpService(mIUpdateHttpService);
        }
        return updateEntity;
    }

    /**
     * 发现新版本
     *
     * @param updateEntity 版本更新信息
     * @param updateProxy  版本更新代理
     */
    @Override
    public void findNewVersion(@NonNull UpdateEntity updateEntity, @NonNull IUpdateProxy updateProxy) {
        if (mIUpdatePrompter instanceof DefaultUpdatePrompter) {
            Context context = getContext();
            if (context instanceof FragmentActivity && ((FragmentActivity) context).isFinishing()) {
                XUpdate.onUpdateError(UpdateError.ERROR.PROMPT_ACTIVITY_DESTROY);
            } else {
                mIUpdatePrompter.showPrompt(updateEntity, updateProxy, mPromptEntity);
            }
        } else {
            mIUpdatePrompter.showPrompt(updateEntity, updateProxy, mPromptEntity);
        }
    }

    /**
     * 未发现新版本
     *
     * @param throwable 未发现的原因
     */
    @Override
    public void noNewVersion(Throwable throwable) {
    }

    @Override
    public void startDownload(@NonNull UpdateEntity updateEntity, @Nullable OnFileDownloadListener downloadListener) {
        updateEntity.setIUpdateHttpService(mIUpdateHttpService);
        mIUpdateDownloader.startDownload(updateEntity, downloadListener);
    }

    /**
     * 后台下载
     */
    @Override
    public void backgroundDownload() {
        mIUpdateDownloader.backgroundDownload();
    }

    @Override
    public void cancelDownload() {
        mIUpdateDownloader.cancelDownload();
    }

    @Override
    public void recycle() {
        mIUpdateHttpService = null;
        mIUpdateChecker = null;
        mIUpdateParser = null;
        mIUpdateDownloader = null;
        mOnFileDownloadListener = null;
        mIUpdatePrompter = null;
    }

    //============================对外提供的自定义使用api===============================//

    /**
     * 为外部提供简单的下载功能
     *
     * @param downloadUrl      下载地址
     * @param downloadListener 下载监听
     */
    public void download(String downloadUrl, @Nullable OnFileDownloadListener downloadListener) {
        startDownload(refreshParams(new UpdateEntity().setDownloadUrl(downloadUrl)), downloadListener);
    }

    /**
     * 直接更新，不使用版本更新检查器
     *
     * @param updateEntity 版本更新信息
     */
    public void update(UpdateEntity updateEntity) {
        mUpdateEntity = refreshParams(updateEntity);
        try {
            UpdateUtils.processUpdateEntity(mUpdateEntity, "这里调用的是直接更新方法，因此没有json!", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //============================构建者===============================//

    /**
     * 版本更新管理构建者
     */
    public static class Builder {
        //=======必填项========//
        Context context;
        /**
         * 版本更新网络请求服务API
         */
        IUpdateHttpService updateHttpService;
        /**
         * 版本更新解析器
         */
        IUpdateParser updateParser;
        //===========更新模式================//
        /**
         * 是否只在wifi下进行版本更新检查
         */
        boolean isWifiOnly;
        //===========更新行为================//
        /**
         * 版本更新检查器
         */
        IUpdateChecker updateChecker;
        /**
         * 版本更新提示器参数信息
         */
        PromptEntity promptEntity;
        /**
         * 版本更新提示器
         */
        IUpdatePrompter updatePrompter;
        /**
         * 下载器
         */
        IUpdateDownloader updateDownLoader;
        /**
         * 下载监听
         */
        OnFileDownloadListener onFileDownloadListener;
        /**
         * apk缓存的目录
         */
        String apkCacheDir;

        /**
         * 构建者
         *
         * @param context
         */
        Builder(@NonNull Context context) {
            this.context = context;

            promptEntity = new PromptEntity();

            updateHttpService = XUpdate.getIUpdateHttpService();

            updateChecker = XUpdate.getIUpdateChecker();
            updateParser = XUpdate.getIUpdateParser();
            updatePrompter = XUpdate.getIUpdatePrompter();
            updateDownLoader = XUpdate.getIUpdateDownLoader();

            isWifiOnly = XUpdate.isWifiOnly();
            apkCacheDir = XUpdate.getApkCacheDir();
        }


        /**
         * 设置网络请求的请求服务API
         *
         * @param updateHttpService
         * @return
         */
        public Builder updateHttpService(@NonNull IUpdateHttpService updateHttpService) {
            this.updateHttpService = updateHttpService;
            return this;
        }

        /**
         * 设置apk下载的缓存目录
         *
         * @param apkCacheDir
         * @return
         */
        public Builder apkCacheDir(@NonNull String apkCacheDir) {
            this.apkCacheDir = apkCacheDir;
            return this;
        }

        /**
         * 是否只在wifi下进行版本更新检查
         *
         * @param isWifiOnly
         * @return
         */
        public Builder isWifiOnly(boolean isWifiOnly) {
            this.isWifiOnly = isWifiOnly;
            return this;
        }

        /**
         * 设置版本更新检查器
         *
         * @param updateChecker 版本更新检查器
         * @return
         */
        public Builder updateChecker(@NonNull IUpdateChecker updateChecker) {
            this.updateChecker = updateChecker;
            return this;
        }

        /**
         * 设置版本更新的解析器
         *
         * @param updateParser 版本更新的解析器
         * @return
         */
        public Builder updateParser(@NonNull IUpdateParser updateParser) {
            this.updateParser = updateParser;
            return this;
        }

        /**
         * 设置版本更新提示器
         *
         * @param updatePrompter 版本更新提示器
         * @return
         */
        public Builder updatePrompter(@NonNull IUpdatePrompter updatePrompter) {
            this.updatePrompter = updatePrompter;
            return this;
        }

        /**
         * 设置文件的下载监听
         *
         * @param onFileDownloadListener 文件下载监听
         * @return
         */
        public Builder setOnFileDownloadListener(OnFileDownloadListener onFileDownloadListener) {
            this.onFileDownloadListener = onFileDownloadListener;
            return this;
        }

        /**
         * 设置主题颜色
         *
         * @param themeColor 主题颜色资源
         * @return
         */
        @Deprecated
        public Builder themeColor(@ColorInt int themeColor) {
            promptEntity.setThemeColor(themeColor);
            return this;
        }

        /**
         * 设置主题颜色
         *
         * @param themeColor 主题颜色资源
         * @return
         */
        public Builder promptThemeColor(@ColorInt int themeColor) {
            promptEntity.setThemeColor(themeColor);
            return this;
        }

        /**
         * 设置顶部背景图片
         *
         * @param topResId 顶部背景图片资源
         * @return
         */
        @Deprecated
        public Builder topResId(@DrawableRes int topResId) {
            promptEntity.setTopResId(topResId);
            return this;
        }

        /**
         * 设置顶部背景图片
         *
         * @param topResId 顶部背景图片资源
         * @return
         */
        public Builder promptTopResId(@DrawableRes int topResId) {
            promptEntity.setTopResId(topResId);
            return this;
        }

        /**
         * 设置按钮的文字颜色
         *
         * @param buttonTextColor 按钮的文字颜色
         * @return
         */
        public Builder promptButtonTextColor(@ColorInt int buttonTextColor) {
            promptEntity.setButtonTextColor(buttonTextColor);
            return this;
        }

        /**
         * 设置是否支持后台更新
         *
         * @param supportBackgroundUpdate
         * @return
         */
        public Builder supportBackgroundUpdate(boolean supportBackgroundUpdate) {
            promptEntity.setSupportBackgroundUpdate(supportBackgroundUpdate);
            return this;
        }

        /**
         * 设置版本更新提示器宽度占屏幕的比例，默认是-1，不做约束
         *
         * @param widthRatio
         * @return
         */
        public Builder promptWidthRatio(float widthRatio) {
            promptEntity.setWidthRatio(widthRatio);
            return this;
        }

        /**
         * 设置版本更新提示器高度占屏幕的比例，默认是-1，不做约束
         *
         * @param heightRatio
         * @return
         */
        public Builder promptHeightRatio(float heightRatio) {
            promptEntity.setHeightRatio(heightRatio);
            return this;
        }

        /**
         * 设置版本更新提示器的样式
         *
         * @param promptEntity 版本更新提示器参数信息
         * @return
         */
        public Builder promptStyle(@NonNull PromptEntity promptEntity) {
            this.promptEntity = promptEntity;
            return this;
        }

        /**
         * 设备版本更新下载器
         *
         * @param updateDownLoader
         * @return
         */
        public Builder updateDownLoader(@NonNull IUpdateDownloader updateDownLoader) {
            this.updateDownLoader = updateDownLoader;
            return this;
        }

        /**
         * 构建版本更新管理者
         *
         * @return 版本更新管理者
         */
        public UpdateManager build() {
            UpdateUtils.requireNonNull(this.context, "[UpdateManager.Builder] : context == null");
            UpdateUtils.requireNonNull(this.updateHttpService, "[UpdateManager.Builder] : updateHttpService == null");

            if (TextUtils.isEmpty(apkCacheDir)) {
                apkCacheDir = UpdateUtils.getDefaultDiskCacheDirPath();
            }
            return new UpdateManager(this);
        }

        /**
         * 进行版本更新
         */
        public void update() {
            build().update();
        }

    }

    @Override
    public String toString() {
        return "XUpdate{" +
                ", mApkCacheDir='" + mApkCacheDir + '\'' +
                ", mIsWifiOnly=" + mIsWifiOnly +
                '}';
    }
}
