package com.wangrui.wan.wanandroid;


import com.wangrui.wan.wanandroid.bean.BannerDate;
import com.wangrui.wan.wanandroid.bean.ArticleBean;
import com.wangrui.wan.wanandroid.bean.KnowLeageArticleBean;
import com.wangrui.wan.wanandroid.bean.KnowleageBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetRequest {

    //首页banner
    @GET(Interface.HOME_BANNER)
    Call<BannerDate> getBannerDate();

    //首页文章列表
    @GET(Interface.HOME_ARTICLE)
    Call<ArticleBean> getArticleDate();

    @GET(Interface.KNOWLEAGE_LIST)
    Call<KnowleageBean> getArticleList();

    @GET(Interface.KNOWLEAGE_LIST_ARITICLE)
    Call<KnowLeageArticleBean> getKnowLeageArticle(@Query("cid") int id);


}
