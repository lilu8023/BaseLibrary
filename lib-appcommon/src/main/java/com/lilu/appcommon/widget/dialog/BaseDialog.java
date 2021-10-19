package com.lilu.appcommon.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.Window;

import androidx.appcompat.app.AppCompatDialog;

/**
 * Description:
 *
 * @author lilu0916 on 2021/8/19
 * No one knows this better than me
 */
public class BaseDialog extends AppCompatDialog {

    private boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;
    private boolean canceledOnTouchOutsideSet;

    public BaseDialog(Context context) {
        super(context);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (this.cancelable != cancelable) {
            this.cancelable = cancelable;
            onSetCancelable(cancelable);
        }
    }

    protected void onSetCancelable(boolean cancelable) {

    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !cancelable) {
            cancelable = true;
        }
        canceledOnTouchOutside = cancel;
        canceledOnTouchOutsideSet = true;
    }

    protected boolean shouldWindowCloseOnTouchOutside() {
        if (!canceledOnTouchOutsideSet) {
            TypedArray a =
                    getContext()
                            .obtainStyledAttributes(new int[]{android.R.attr.windowCloseOnTouchOutside});
            canceledOnTouchOutside = a.getBoolean(0, true);
            a.recycle();
            canceledOnTouchOutsideSet = true;
        }
        return canceledOnTouchOutside;
    }

    @Override
    public void dismiss() {
        Context context = getContext();
        if(context instanceof ContextWrapper){
            context = ((ContextWrapper)context).getBaseContext();
        }
        if(context instanceof Activity){
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if(activity.isDestroyed() || activity.isFinishing()){
                    return;
                }
            }
            super.dismiss();
        }else{
            try{
                super.dismiss();
            }catch (Throwable ignore){
            }
        }
    }
}
