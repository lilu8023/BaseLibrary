package com.lilu.apptool.router.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Description:
 *
 * @author lilu0916 on 2021/5/19 10:51
 * No one knows this better than me
 */
public class SchemeFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();

        if(uri != null){
            ARouter.getInstance()
                    .build(uri)
                    .navigation();
        }
        finish();
    }
}
