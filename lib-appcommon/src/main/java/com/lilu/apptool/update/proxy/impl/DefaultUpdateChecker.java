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

package com.lilu.apptool.update.proxy.impl;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.lilu.apptool.update.XUpdate;
import com.lilu.apptool.update.entity.UpdateEntity;
import com.lilu.apptool.update.entity.UpdateError;
import com.lilu.apptool.update.listener.IUpdateParseCallback;
import com.lilu.apptool.update.proxy.IUpdateChecker;
import com.lilu.apptool.update.proxy.IUpdateHttpService;
import com.lilu.apptool.update.proxy.IUpdateProxy;
import com.lilu.apptool.update.service.DownloadService;
import com.lilu.apptool.update.utils.UpdateUtils;

import java.util.Map;

import static com.lilu.apptool.update.entity.UpdateError.ERROR.CHECK_UPDATING;


/**
 * 默认版本更新检查者
 *
 * @author xuexiang
 * @since 2018/7/2 下午10:21
 */
public class DefaultUpdateChecker implements IUpdateChecker {

    @Override
    public void onBeforeCheck() {

    }

    @Override
    public void checkVersion(final @NonNull IUpdateProxy updateProxy) {
        //如果正在下载或下载提示框已经显示了
        if (DownloadService.isRunning() || XUpdate.isShowUpdatePrompter()) {
            updateProxy.onAfterCheck();
            XUpdate.onUpdateError(CHECK_UPDATING);
            return;
        }

        //获取
        updateProxy.getIUpdateHttpService().haveNewVersion(new IUpdateHttpService.Callback() {
            @Override
            public void onSuccess(UpdateEntity updateEntity) {
                //版本更新接口http成功
                updateProxy.onAfterCheck();
                if (updateEntity != null) {
                    if (updateEntity.isHasUpdate()) {
                        //校验是否是已忽略版本
                        if (UpdateUtils.isIgnoreVersion(updateProxy.getContext(), updateEntity.getVersionName())) {
                            XUpdate.onUpdateError(UpdateError.ERROR.CHECK_IGNORED_VERSION);
                            //校验apk下载缓存目录是否为空
                        } else if (TextUtils.isEmpty(updateEntity.getApkCacheDir())) {
                            XUpdate.onUpdateError(UpdateError.ERROR.CHECK_APK_CACHE_DIR_EMPTY);
                        } else {
                            //有新版本需要提示更新
                            updateProxy.findNewVersion(updateEntity, updateProxy);
                        }
                    } else {
                        //暂无新版本
                        updateProxy.noNewVersion(null);
                    }
                } else {
                    //json数据解析错误，有可能是接口返回的数据有问题导致无法按客户端实体类解析
                    XUpdate.onUpdateError(UpdateError.ERROR.CHECK_PARSE);
                }
            }

            @Override
            public void onError(Throwable error) {
                //版本更新接口http失败
                updateProxy.onAfterCheck();
                XUpdate.onUpdateError(UpdateError.ERROR.CHECK_NET_REQUEST, error.getMessage());
            }
        });
    }

    @Override
    public void onAfterCheck() {

    }

}
