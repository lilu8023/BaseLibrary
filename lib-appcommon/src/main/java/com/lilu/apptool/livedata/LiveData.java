package com.lilu.apptool.livedata;

import com.lilu.apptool.http.exception.ApiException;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/2
 * No one knows this better than me
 */
public class LiveData<T> {

    private T data;
    private LiveStatus status;
    private Throwable error;
    private ApiException exception;


    public LiveData<T> success(T t){
        this.data = t;
        this.status = LiveStatus.SUCCESS;
        this.error = null;
        return this;
    }

    public LiveData<T> error(Throwable e){
        this.data = null;
        this.status = LiveStatus.ERROR;
        this.error = e;
        return this;
    }

    public T getData() {
        return data;
    }

    public LiveStatus getStatus() {
        return status;
    }

    public Throwable getError() {
        return error;
    }
}
