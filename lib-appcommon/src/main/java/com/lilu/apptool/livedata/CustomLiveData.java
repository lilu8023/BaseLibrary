package com.lilu.apptool.livedata;

import androidx.lifecycle.MutableLiveData;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/2
 * No one knows this better than me
 */
public class CustomLiveData<T> extends MutableLiveData<LiveData<T>> {

    public void postSuccess(T data){
        postValue(new LiveData<T>().success(data));
    }

    public void postError(Throwable e){
        postValue(new LiveData<T>().error(e));
    }
}
