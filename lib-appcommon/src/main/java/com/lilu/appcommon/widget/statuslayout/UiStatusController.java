package com.lilu.appcommon.widget.statuslayout;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lilu.appcommon.widget.statuslayout.annotation.UiStatus;
import com.lilu.appcommon.widget.statuslayout.controller.IUiStatusController;
import com.lilu.appcommon.widget.statuslayout.controller.IUiStatusProvider;
import com.lilu.appcommon.widget.statuslayout.controller.UiStatusProviderImpl;
import com.lilu.appcommon.widget.statuslayout.listener.OnCompatRetryListener;
import com.lilu.appcommon.widget.statuslayout.listener.OnLayoutStatusChangedListener;
import com.lilu.appcommon.widget.statuslayout.listener.OnRetryListener;
import com.lilu.appcommon.widget.statuslayout.utils.BindHelp;
import com.lilu.appcommon.widget.statuslayout.widget.UiStatusLayout;


/**
 * Created by 段露 on 2019/01/31 16:16.
 *
 * @author 段露
 * @version 1.0.0
 * @class UiStatusController
 * @describe 视图状态控制器.
 */
public class UiStatusController implements IUiStatusProvider, IUiStatusController {

    private IUiStatusProvider mIUiStatusProvider;
    private UiStatusLayout mUiStatusLayout;

    private UiStatusController() {
        mIUiStatusProvider = new UiStatusProviderImpl();
        UiStatusManager.getInstance().copyConfig((UiStatusProviderImpl) mIUiStatusProvider);
    }

    public static UiStatusController get() {
        return new UiStatusController();
    }

    public UiStatusController bind(@NonNull Object target) {
        mUiStatusLayout = BindHelp.bind(target);
        mUiStatusLayout.setUiStatusProvider(this);
        //默认显示加载中视图.
        changeUiStatus(UiStatus.LOADING);
        return this;
    }

    public View bindFragment(@NonNull View fragmentView) {
        mUiStatusLayout = BindHelp.bindFragmentView(fragmentView);
        mUiStatusLayout.setUiStatusProvider(this);
        //默认显示加载中视图.
        changeUiStatus(UiStatus.LOADING);
        return mUiStatusLayout;
    }

    @Override
    public IUiStatusProvider addUiStatusConfig(@UiStatus int uiStatus, @LayoutRes int layoutResId) {
        mIUiStatusProvider.addUiStatusConfig(uiStatus, layoutResId);
        return this;
    }

    @Override
    public UiStatusController addUiStatusConfig(@UiStatus int uiStatus, @LayoutRes int layoutResId, @IdRes int retryTriggerViewId, OnRetryListener listener) {
        mIUiStatusProvider.addUiStatusConfig(uiStatus, layoutResId, retryTriggerViewId, listener);
        return this;
    }

    @Override
    public Postcard getUiStatusConfig(@UiStatus int uiStatus) {
        return mIUiStatusProvider.getUiStatusConfig(uiStatus);
    }

    @Override
    public IUiStatusProvider setWidgetMargin(@UiStatus @IntRange(from = 7, to = 9) int uiStatus, int topMarginPx, int bottomMarginPx) {
        mIUiStatusProvider.setWidgetMargin(uiStatus, topMarginPx, bottomMarginPx);
        return this;
    }

    @Override
    public UiStatusController setListener(@UiStatus int uiStatus, OnRetryListener listener) {
        mIUiStatusProvider.setListener(uiStatus, listener);
        return this;
    }

    @Override
    public UiStatusController setOnLayoutStatusChangedListener(OnLayoutStatusChangedListener listener) {
        mIUiStatusProvider.setOnLayoutStatusChangedListener(listener);
        return this;
    }

    @Override
    public OnLayoutStatusChangedListener getOnLayoutStatusChangedListener() {
        return mIUiStatusProvider.getOnLayoutStatusChangedListener();
    }

    @Override
    public IUiStatusProvider setOnCompatRetryListener(OnCompatRetryListener compatRetryListener) {
        return mIUiStatusProvider.setOnCompatRetryListener(compatRetryListener);
    }

    @Override
    public OnCompatRetryListener getOnCompatRetryListener() {
        return mIUiStatusProvider.getOnCompatRetryListener();
    }

    @Override
    public UiStatusController setAutoLoadingWithRetry(boolean isAutoLoadingWithRetry) {
        mIUiStatusProvider.setAutoLoadingWithRetry(isAutoLoadingWithRetry);
        return this;
    }

    @Override
    public boolean isAutoLoadingWithRetry() {
        return mIUiStatusProvider.isAutoLoadingWithRetry();
    }

    ///////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public View getView(@UiStatus int uiStatus) {
        return mUiStatusLayout.getView(uiStatus);
    }

    @Nullable
    @Override
    public View getViewStrong(@UiStatus int uiStatus) {
        return mUiStatusLayout.getViewStrong(uiStatus);
    }

    @Override
    public void changeUiStatus(@UiStatus int uiStatus) {
        mUiStatusLayout.changeUiStatus(uiStatus);
    }

    @Override
    public void changeUiStatusIgnore(@UiStatus int uiStatus) {
        mUiStatusLayout.changeUiStatusIgnore(uiStatus);
    }

    @Override
    public int getCurrentUiStatus() {
        return mUiStatusLayout.getCurrentUiStatus();
    }

    @Override
    public void showWidget(@UiStatus @IntRange(from = 7, to = 9) int uiStatus) {
        mUiStatusLayout.showWidget(uiStatus);
    }

    @Override
    public void showWidget(@UiStatus @IntRange(from = 7, to = 9) int uiStatus, int duration) {
        mUiStatusLayout.showWidget(uiStatus, duration);
    }

    @Override
    public void hideWidget(@IntRange(from = 7, to = 9) @UiStatus int uiStatus) {
        mUiStatusLayout.hideWidget(uiStatus);
    }

    @Override
    public boolean isVisibleUiStatus(@UiStatus int uiStatus) {
        return mUiStatusLayout.isVisibleUiStatus(uiStatus);
    }

}
