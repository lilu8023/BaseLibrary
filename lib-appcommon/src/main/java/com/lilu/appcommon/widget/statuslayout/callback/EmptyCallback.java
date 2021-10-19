package com.lilu.appcommon.widget.statuslayout.callback;

import android.content.Context;
import android.view.View;

import com.lilu.appcommon.R;

/**
 * Description:
 *
 * @author lilu0916 on 2021/8/18
 * No one knows this better than me
 */
public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }
}
