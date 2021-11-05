package com.lilu.appcommon.webview.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lilu.appcommon.R;
import com.lilu.appcommon.activity.BaseActivity;
import com.lilu.appcommon.constant.Constants;
import com.lilu.apptool.router.RouterPath;

/**
 * Description:
 *
 * @author lilu0916 on 2021/8/17
 * No one knows this better than me
 */
@Route(path = RouterPath.ACTIVITY_WEB)
public class WebViewActivity extends BaseActivity {


    @Autowired(name = Constants.WEB_URL)
    String url;

    private ProgressBar web_pb;
    private WebView baseWeb;
    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        web_pb = findViewById(R.id.web_pb);
        baseWeb = findViewById(R.id.wb_base);

        showToolbar(false);
        initWebView();
        ARouter.getInstance().inject(this);

        baseWeb.loadUrl(url);

        showSuccess();

    }

    private void initWebView(){

        baseWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                web_pb.setProgress(100);


            }

            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                return super.onRenderProcessGone(view, detail);
            }
        });

        baseWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {


                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                web_pb.setProgress(newProgress);
            }
        });

    }
}
