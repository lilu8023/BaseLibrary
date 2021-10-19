package com.lilu.apptool.http.subsciber;

import com.lilu.apptool.http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/1
 * No one knows this better than me
 */
public abstract class BaseSubscriber<T> implements Observer<T> {

    public abstract void onError(ApiException e);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }


    @Override
    public void onError(Throwable e) {
        onError(ApiException.handleException(e));
    }

    @Override
    public void onComplete() {

    }

}
