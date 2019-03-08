package com.wangrui.wan.wanandroid;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication Context;

    @Override
    public void onCreate() {
        Context = this;
        super.onCreate();
    }

    public static synchronized MyApplication getInstance() {
        return Context;
    }
}
