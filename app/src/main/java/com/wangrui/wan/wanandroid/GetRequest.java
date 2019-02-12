package com.wangrui.wan.wanandroid;


import com.wangrui.wan.wanandroid.bean.BannerDate;
import com.wangrui.wan.wanandroid.bean.ArticleBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest {

    //首页banner
    @GET(Interface.HOME_BANNER)
    Call<BannerDate> getBannerDate();

    //首页文章列表
    @GET(Interface.HOME_ARTICLE)
    Call<ArticleBean> getArticleDate();


}
