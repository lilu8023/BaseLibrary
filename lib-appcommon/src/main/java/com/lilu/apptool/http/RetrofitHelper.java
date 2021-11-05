package com.lilu.apptool.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:
 *
 * @author lilu0916 on 2021/11/5
 * No one knows this better than me
 */
public class RetrofitHelper {

    private Retrofit mRetrofit;

    private RetrofitHelper() {

        initRetrofit();

    }

    private static class RetrofitHelperHolder{

        private static RetrofitHelper instance = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance(){

        return RetrofitHelperHolder.instance;

    }


    private  OkHttpClient initClient(){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .build();

        return client;
    }

    private void initRetrofit(){

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .client(initClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }




    public <A> A create(Class<A> clazz){

        return mRetrofit.create(clazz);

    }
}
