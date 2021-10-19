package com.lilu.appcommon.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lilu.appcommon.R;
import com.lilu.appcommon.widget.statuslayout.IStatus;
import com.lilu.appcommon.widget.statuslayout.callback.EmptyCallback;
import com.lilu.appcommon.widget.statuslayout.callback.ErrorCallback;
import com.lilu.appcommon.widget.statuslayout.callback.LoadingCallback;
import com.lilu.appcommon.widget.statuslayout.callback.SuccessCallback;
import com.lilu.appcommon.widget.statuslayout.core.LoadService;
import com.lilu.appcommon.widget.statuslayout.core.LoadSir;
import com.lilu.apptool.utils.StringUtils;

import java.util.List;

/**
 * Description:
 *
 * @author lilu0916 on 2021/4/15 14:34
 * No one knows this better than me
 */
public abstract class BaseFragment extends Fragment implements IFragmentVisibility, IStatus {

    // Fragment当前是否对用户可见。
    private boolean mIsFragmentVisible = false;
    // Fragment当前是否是第一次对用户可见。
    private boolean mIsFragmentVisibleFirst = true;


    public abstract @LayoutRes int getRootView();

    public abstract void init(@NonNull View rootView);

    protected View rootView;

    private LoadService statusLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(getRootView(),container,false);
        }

        statusLayout = LoadSir.getDefault().register(rootView);
        init(rootView);

        return statusLayout.getLoadLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        determineFragmentVisible();
    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onPause() {
        super.onPause();

        determineFragmentInvisible();
    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            determineFragmentInvisible();
        } else {
            determineFragmentVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            determineFragmentVisible();
        } else {
            determineFragmentInvisible();
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return mIsFragmentVisible;
    }

    @Override
    public void onVisibleExceptFirst() {

    }

    private void determineFragmentVisible(){
        Fragment parent = getParentFragment();
        if(parent instanceof BaseFragment){
            if(!((BaseFragment) parent).isVisibleToUser()){
                return;
            }
        }

        if(isResumed() && !isHidden() && getUserVisibleHint() && !mIsFragmentVisible){
            mIsFragmentVisible = true;
            onVisible();
            if(mIsFragmentVisibleFirst){
                mIsFragmentVisibleFirst = false;
                onVisibleFirst();
            }else{
                onVisibleExceptFirst();
            }
            determineChildFragmentVisible();
        }
    }

    private void determineFragmentInvisible() {
        if (mIsFragmentVisible) {
            mIsFragmentVisible = false;
            onInvisible();
            determineChildFragmentInvisible();
        }
    }

    private void determineChildFragmentVisible() {
        List<Fragment> childList = getChildFragmentManager().getFragments();
        for(Fragment fragment : childList){
            if(fragment instanceof BaseFragment){
                ((BaseFragment) fragment).determineFragmentVisible();
            }
        }
    }

    private void determineChildFragmentInvisible() {
        List<Fragment> childList = getChildFragmentManager().getFragments();
        for(Fragment fragment : childList){
            if(fragment instanceof BaseFragment){
                ((BaseFragment) fragment).determineFragmentInvisible();
            }
        }
    }

    /**
     * 显示成功状态
     */
    @Override
    public void showSuccess(){
        if(statusLayout != null){
            statusLayout.showCallback(SuccessCallback.class);
        }
    }

    /**
     * 显示加载中状态
     */
    @Override
    public void showLoading(){

        if(statusLayout != null){
            statusLayout.showCallback(LoadingCallback.class);
        }
    }

    /**
     * 显示空，暂无内容状态
     */
    @Override
    public void showEmpty(){
        showEmpty("");
    }

    @Override
    public void showEmpty(String emptyMsg) {
        if(statusLayout != null){
            statusLayout.showCallback(EmptyCallback.class);
        }
    }

    /**
     * 显示默认错误状态
     */
    @Override
    public void showError() {
        showError("默认的错误",null);
    }

    /**
     * 显示错误状态
     * @param errorMsg  错误信息
     * @param listener  错误点击事件
     */
    @Override
    public void showError(String errorMsg, View.OnClickListener listener) {
        if(statusLayout != null){
            //动态修改错误状态的内容
            statusLayout.setCallBack(ErrorCallback.class, (context, view) -> {
                if(!StringUtils.isEmpty(errorMsg)){
                    TextView textView = view.findViewById(R.id.status_error_tv);
                    textView.setText(errorMsg);
                }

                if(listener != null){
                    view.findViewById(R.id.status_error_bt).setOnClickListener(listener);
                }

            });
            statusLayout.showCallback(ErrorCallback.class);
        }
    }
}
