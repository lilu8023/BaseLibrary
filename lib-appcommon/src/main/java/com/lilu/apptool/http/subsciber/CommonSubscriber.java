package com.lilu.apptool.http.subsciber;

import com.lilu.apptool.http.entity.BaseEntity;
import com.lilu.apptool.http.exception.ApiException;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/1
 * No one knows this better than me
 */
public abstract class CommonSubscriber<T> extends BaseSubscriber<BaseEntity<T>> {

    public abstract void onSuccess(T data);

    @Override
    public void onNext(BaseEntity<T> baseEntity) {
        super.onNext(baseEntity);

        if(baseEntity.getErrorCode() == 0){
            onSuccess(baseEntity.getData());
        }else{
            onError(new ApiException(new Throwable(baseEntity.getErrorMsg()),baseEntity.getErrorCode()));
        }


    }
}
