package com.wangrui.wan.wanandroid.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ReceivedCookiesInterceptor implements Interceptor {

    private Context context;
    private  HashSet<String> cookies   = new HashSet<>();

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookies);
            editor.apply();
        }
//        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//            final StringBuffer cookieBuffer = new StringBuffer();
//            Observable.from(originalResponse.headers("Set-Cookie"))
//                    .map(new Func1<String, String>() {
//                        @Override
//                        public String call(String s) {
//                            String[] cookieArray = s.split(";");
//                            return cookieArray[0];
//                        }
//                    })
//                    .subscribe(new Action1<String>() {
//                        @Override
//                        public void call(String cookie) {
//                            cookieBuffer.append(cookie).append(";");
//                        }
//                    });

        return originalResponse;
    }
}
