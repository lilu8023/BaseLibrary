package com.lilu.appcommon.widget.statuslayout;

import android.view.View;

/**
 * Description:
 *
 * @author lilu0916 on 2021/8/18
 * No one knows this better than me
 */
public interface IStatus {

    void showLoading();

    void showSuccess();

    void showEmpty();

    void showEmpty(String emptyMsg);

    void showError();

    void showError(String errorMsg, View.OnClickListener retryListener);
}
