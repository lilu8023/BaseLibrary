package com.lilu.appcommon.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.lilu.appcommon.R;
import com.lilu.appcommon.widget.statuslayout.UiStatusController;
import com.lilu.appcommon.widget.statuslayout.annotation.UiStatus;
import com.lilu.apptool.utils.StringUtils;
import com.noober.background.BackgroundLibrary;


/**
 * Description:
 *  基础activity
 *  1、封装基础布局
 *  2、封装基础状态
 * @author lilu0916 on 2021/4/15 14:34
 * No one knows this better than me
 */
public abstract class BaseActivity extends AppCompatActivity{

    /**
     * 子类布局
     * 子类需要实现
     * @return 子类布局ID 必须是layout文件
     */
    protected abstract @LayoutRes int getContentView();

    /**
     * 子类入口
     * 子类需要实现
     * @param savedInstanceState 缓存状态
     */
    protected abstract void init(Bundle savedInstanceState);


    /**
     *  标题栏B
     */
    private Toolbar tbBase;

    /**
     * 状态布局管理器
     */
    private UiStatusController statusController;

    protected View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //
        BackgroundLibrary.inject(this);

        super.onCreate(savedInstanceState);

        //设置基础父布局
        View rootView = View.inflate(this, R.layout.activity_base,null);
        setContentView(rootView);

        //设置状态栏
        initStatus();

        //获取基础界面控件
        tbBase = rootView.findViewById(R.id.tb_base);
        FrameLayout flBase = rootView.findViewById(R.id.fl_base);

        //初始化标题栏
        initToolbar(tbBase);

        //将子类目标布局解析加载到baseFrameLayout中
        mContentView = View.inflate(this, getContentView(), null);
        if(mContentView != null){
            //将实际内容加载到内容布局中
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            flBase.addView(mContentView,params);

            statusController = UiStatusController.get().bind(flBase);
        }

        //子类初始化
        init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 初始化状态栏
     */
    protected void initStatus(){
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_398DEF)
                .init();
    }

    /**
     * 初始化标题栏
     */
    protected void initToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    protected void setTitle(String title){
        TextView titleTv = findViewById(R.id.tb_title_tv);
        titleTv.setText(title);
    }
    /**
     * 是否显示actionBar
     * 默认是显示的
     * 针对主页，主页可能不需要actionbar，会在主页
     * @param isShow 是否显示标识
     */
    protected void showToolbar(boolean isShow){
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            if(isShow){
                bar.show();
            }else{
                bar.hide();
            }
        }

    }

    /**
     * 显示成功状态
     */
    public void showSuccess(){

        if(statusController != null){
            statusController.changeUiStatus(UiStatus.CONTENT);
        }
    }

    /**
     * 显示加载中状态
     */
    public void showLoading(){

        if(statusController != null){
            statusController.changeUiStatus(UiStatus.LOADING);
        }
    }

    /**
     * 显示空，暂无内容状态
     */
    public void showEmpty(){
        showEmpty("");
    }

    public void showEmpty(String emptyMsg) {
        if(statusController != null){
            statusController.changeUiStatus(UiStatus.EMPTY);
        }
    }

    /**
     * 显示默认错误状态
     */
    public void showError() {
        showError("默认的错误",null);
    }

    /**
     * 显示错误状态
     * @param errorMsg  错误信息
     * @param listener  错误点击事件
     */
    public void showError(String errorMsg, View.OnClickListener listener) {

        if(statusController != null){
            statusController.changeUiStatus(UiStatus.LOAD_ERROR);
        }
    }
}
