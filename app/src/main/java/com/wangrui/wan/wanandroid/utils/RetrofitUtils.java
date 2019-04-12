package com.wangrui.wan.wanandroid.utils;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wangrui.wan.wanandroid.interceptor.ReceivedCookiesInterceptor;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wangrui.wan.wanandroid.Interface.BASE_URL;

public class RetrofitUtils {
    public static final int TIMEOUT = 60; //超时时间
    private static volatile RetrofitUtils mInstance;
    private Retrofit mRetrofit;

    public static RetrofitUtils getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                }
            }
        }
        return mInstance;
    }

    private RetrofitUtils() {
        initRetrofit();
    }

    private RetrofitUtils(Context context) {
        initRetrofitLogin(context);
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
//        ClearableCookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getInstance()));
        OkHttpClient client = builder.build();
//        client.interceptors().add(new ReceivedCookiesInterceptor(MyApplication.getInstance()));
//        client.interceptors().add(new AddCookiesInterceptor(MyApplication.getInstance()));
        mRetrofit = new Retrofit.Builder()
                // 设置请求的域名
                .baseUrl(BASE_URL)
                // 设置解析转换工厂，用自己定义的
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private void initRetrofitLogin(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        client.interceptors().add(new ReceivedCookiesInterceptor(context));

        mRetrofit = new Retrofit.Builder()
                // 设置请求的域名
                .baseUrl(BASE_URL)
                // 设置解析转换工厂，用自己定义的
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    /**
     * 创建API
     */
    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}
