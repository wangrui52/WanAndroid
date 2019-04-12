package com.wangrui.wan.wanandroid.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

public class AddCookiesInterceptor implements Interceptor {
    private Context context;
    private HashSet<String> cookies   = new HashSet<>();

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        cookies = (HashSet<String>) sharedPreferences.getStringSet("cookie",new HashSet<>());
//        Observable.just(sharedPreferences.getString("cookie", ""))
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String cookie) {
//                        //添加cookie
//                        builder.addHeader("Cookie", cookie);
//                    }
//                });
        for (String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }
}
