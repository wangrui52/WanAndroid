package com.wangrui.wan.wanandroid;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest {

    @GET("/banner/json")
    Call<BannerDate> getBannerDate();
}
