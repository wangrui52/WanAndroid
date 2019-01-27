package com.wangrui.wan.wanandroid;


import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest {

    @GET(Interface.HOME_BANNER)
    Call<BannerDate> getBannerDate();


}
