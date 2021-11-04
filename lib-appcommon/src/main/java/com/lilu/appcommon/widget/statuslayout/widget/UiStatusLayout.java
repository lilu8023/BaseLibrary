package com.lilu.appcommon.widget.statuslayout.widget;

import android.animation.LayoutTransition;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lilu.appcommon.R;
import com.lilu.appcommon.widget.statuslayout.Postcard;
import com.lilu.appcommon.widget.statuslayout.UiStatusConfig;
import com.lilu.appcommon.widget.statuslayout.UiStatusController;
import com.lilu.appcommon.widget.statuslayout.UiStatusNetworkStatusProvider;
import com.lilu.appcommon.widget.statuslayout.annotation.UiStatus;
import com.lilu.appcommon.widget.statuslayout.controller.IUiStatusController;
import com.lilu.appcommon.widget.statuslayout.listener.OnCompatRetryListener;
import com.lilu.appcommon.widget.statuslayout.listener.OnLayoutStatusChangedListener;


/**
 * Created by 段露 on 2018/11/02 17:09.
 *
 * @author 段露
 * @version 1.0.0
 * @class StatusLayout
 * @describe 状态布局控件.
 */
public class UiStatusLayout extends FrameLayout implements IUiStatusController, View.OnClickListener {

    private SparseArray<View> mUiStatusViewCache;

    @UiStatus
    private int mCurrentUiStatus = 0;

    private UiStatusController mUiStatusController;

    private Object mTarget;
    private View mContentUiView;

    public UiStatusLayout(@NonNull View contentUiView, @NonNull Object target) {
        super(contentUiView.getContext());
        this.mTarget = target;
        this.mContentUiView = contentUiView;
        mUiStatusViewCache = new SparseArray<>(UiStatusConfig.UI_STATUS_SIZE);

        LayoutInflater.from(getContext()).inflate(R.layout.layout_status_controller_layout, this);

        mContentUiView.setVisibility(View.GONE);
        cacheUiView(UiStatus.CONTENT, mContentUiView);
        super.addView(mContentUiView, 0);

        setLayoutTransition(new LayoutTransition());
    }

    public void setUiStatusProvider(@NonNull UiStatusController controller) {
        this.mUiStatusController = controller;
    }

    @Nullable
    @Override
    public View getView(@UiStatus int uiStatus) {
        return getUiViewByUiStatusFromCache(uiStatus);
    }

    @Nullable
    @Override
    public View getViewStrong(@UiStatus int uiStatus) {
        return getUiViewByUiStatusAndInitGone(uiStatus);
    }

    @Override
    public void changeUiStatus(@UiStatus int uiStatus) {
        _changeUiStatus(uiStatus, false);
    }

    @Override
    public void changeUiStatusIgnore(@UiStatus int uiStatus) {
        _changeUiStatus(uiStatus, true);
    }

    private void _changeUiStatus(@UiStatus int uiStatus, boolean ignore) {

        //Widget重定向.
        if (isWidget(uiStatus)) {
            if (isVisibleUiStatus(uiStatus)) {
                hideWidget(uiStatus);
            } else {
                showWidget(uiStatus);
            }
            return;
        }

        //网络错误时重定向.
        if (!UiStatusNetworkStatusProvider.getInstance().isConnection(getContext())) {
            uiStatus = UiStatus.NETWORK_ERROR;
        }

        if (mCurrentUiStatus == uiStatus) {
            //nothing.
            return;
        } else if (ignore && UiStatus.CONTENT == mCurrentUiStatus) {
            //nothing.
            return;
        }

        this.handleVisibilityChange(mCurrentUiStatus, View.GONE);
        mCurrentUiStatus = uiStatus;
        this.handleVisibilityChange(mCurrentUiStatus, View.VISIBLE);
    }

    @UiStatus
    @Override
    public int getCurrentUiStatus() {
        return this.mCurrentUiStatus;
    }

    @Override
    public boolean isVisibleUiStatus(@UiStatus int uiStatus) {
        //只需要从缓存获取就好了,缓存都没有那肯定是没有显示过了.
        View temp = getUiViewByUiStatusFromCache(uiStatus);
        return null != temp && View.VISIBLE == temp.getVisibility();
    }

    @Override
    public void showWidget(@UiStatus int uiStatus) {
        if (isWidget(uiStatus)) {
            this.handleVisibilityChange(uiStatus, View.VISIBLE);
        }
    }

    @Override
    public void showWidget(final @UiStatus int uiStatus, int duration) {
        if (isWidget(uiStatus)) {
            this.handleVisibilityChange(uiStatus, View.VISIBLE);
            if (duration > 0) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideWidget(uiStatus);
                    }
                }, duration);
            }
        }
    }

    @Override
    public void hideWidget(@UiStatus int uiStatus) {
        if (isWidget(uiStatus)) {
            this.handleVisibilityChange(uiStatus, View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        dispatchTrigger((int) v.getTag(), v);
    }

    private void dispatchTrigger(@UiStatus int uiStatus, @NonNull View trigger) {
        Postcard postcard = mUiStatusController.getUiStatusConfig(uiStatus);
        if (trigger.getId() != postcard.triggerViewId) return;

        OnCompatRetryListener compatRetryListener = mUiStatusController.getOnCompatRetryListener();

        //检查是否需要显示加载中状态.
        if (null != postcard.retryListener || null != compatRetryListener) {
            if (mUiStatusController.isAutoLoadingWithRetry()
                    && (UiStatus.NETWORK_ERROR == uiStatus
                    || UiStatus.LOAD_ERROR == uiStatus
                    || UiStatus.EMPTY == uiStatus)) {
                changeUiStatusIgnore(UiStatus.LOADING);
            }
        }
        //重试.
        if (null != postcard.retryListener) {
            postcard.retryListener.onUiStatusRetry(mTarget, mUiStatusController, trigger);
        } else if (null != compatRetryListener) {
            compatRetryListener.onUiStatusRetry(uiStatus, mTarget, mUiStatusController, trigger);
        }
    }

    private boolean isWidget(@UiStatus int uiStatus) {
        return UiStatus.WIDGET_NETWORK_ERROR == uiStatus
                || UiStatus.WIDGET_ELFIN == uiStatus
                || UiStatus.WIDGET_FLOOR == uiStatus
                || UiStatus.WIDGET_FLOAT == uiStatus;
    }

    private void handleVisibilityChange(@UiStatus int uiStatus, int visibility) {
        setUiVisibility(uiStatus, getUiViewByUiStatus(uiStatus), visibility);
    }

    private void setUiVisibility(@UiStatus int uiStatus, View view, int visibility) {
        if (null == view) return;

        OnLayoutStatusChangedListener listener = mUiStatusController.getOnLayoutStatusChangedListener();

        if (null != listener) {
            listener.onPrepareChanged(mTarget, view, uiStatus, View.VISIBLE == visibility);
        }

        view.setVisibility(visibility);

        if (null != listener) {
            listener.onChangedComplete(mTarget, view, uiStatus, View.VISIBLE == visibility);
        }
    }

    private View getUiViewByUiStatus(@UiStatus int uiStatus) {
        View temp = getUiViewByUiStatusFromCache(uiStatus);
        if (null == temp) temp = findUiViewByUiStatus(uiStatus);
        return temp;
    }

    private View getUiViewByUiStatusAndInitGone(@UiStatus int uiStatus) {
        View temp = getUiViewByUiStatusFromCache(uiStatus);
        //如果部位null的话说明初始化过,如果是VISIBLE的那么它肯定是显示的不应该GONE,所以直接返回.
        if (null != temp) return temp;

        temp = findUiViewByUiStatus(uiStatus);
        //如果走到这里并且部位null的话,因为在findUiViewByUiStatus方法里偷懒没有隐藏掉,所以在这里GONE.
        if (null != temp) temp.setVisibility(View.GONE);
        return temp;
    }

    private View findUiViewByUiStatus(@UiStatus int uiStatus) {

        if (UiStatus.CONTENT == uiStatus) {
            return mContentUiView;
        }

        int viewStubId = UiStatusConfig.ID_ROUTER.get(uiStatus);

        Postcard postcard = mUiStatusController.getUiStatusConfig(uiStatus);
        if (null == postcard || postcard.layoutResId <= 0) return null;

        ViewStub viewStub = findViewById(viewStubId);
        viewStub.setLayoutResource(postcard.layoutResId);
        View view = viewStub.inflate();
        //这里获取的view其实是View.VISIBLE的,但是考虑到按照流程如果走到初始化的话说明是需要显示该View,
        //那么在这里GONE后又要被设置为VISIBLE,那么这个操作将显得没有任何意义,还会带来额外的开销,
        //那么索性就不隐藏了.

        if (isWidget(uiStatus)) {
            if (postcard.topMarginPx > 0) {
                ((MarginLayoutParams) view.getLayoutParams()).topMargin = postcard.topMarginPx;
            }
            if (postcard.bottomMarginPx > 0) {
                ((MarginLayoutParams) view.getLayoutParams()).bottomMargin = postcard.bottomMarginPx;
            }
        }

        View triggerView = view.findViewById(postcard.triggerViewId);
        if (null != triggerView) {
            triggerView.setTag(uiStatus);
            triggerView.setOnClickListener(this);
        }
        cacheUiView(uiStatus, view);
        return view;
    }

    private View getUiViewByUiStatusFromCache(@UiStatus int uiStatus) {
        return mUiStatusViewCache.get(uiStatus);
    }

    private void cacheUiView(@UiStatus int uiStatus, View view) {
        if (null == view) return;
        mUiStatusViewCache.put(uiStatus, view);
    }

}