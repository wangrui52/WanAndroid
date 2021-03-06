package com.wangrui.wan.wanandroid;


import com.wangrui.wan.wanandroid.bean.BannerDate;
import com.wangrui.wan.wanandroid.bean.ArticleBean;
import com.wangrui.wan.wanandroid.bean.KnowLeageArticleBean;
import com.wangrui.wan.wanandroid.bean.KnowleageBean;
import com.wangrui.wan.wanandroid.bean.NavigationBean;
import com.wangrui.wan.wanandroid.bean.WXArticleAuthorBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetRequest {

    //首页banner
    @GET(Interface.HOME_BANNER)
    Call<BannerDate> getBannerDate();

    //首页文章列表
    @GET(Interface.HOME_ARTICLE)
    Call<ArticleBean> getArticleDate(@Path("pageId") int pageId);

    @GET(Interface.KNOWLEAGE_LIST)
    Call<KnowleageBean> getArticleList();

    @GET(Interface.KNOWLEAGE_LIST_ARITICLE)
    Call<KnowLeageArticleBean> getKnowLeageArticle(@Query("cid") int id);

    //公众号文章列表
    @GET(Interface.GONGZGONGHAO_ARITICLE)
    Call<WXArticleAuthorBean> getWXArticle();
    //公众号文章详情
    @GET(Interface.GONGZHONG_DETIlE)
    Call<KnowLeageArticleBean> getGZArticle(@Path("chapterId") int chapterId,@Path("pageId") int pageId);
    //导航页
    @GET(Interface.NAVIGATION_LINK)
    Call<NavigationBean> getNavigationDate();
    //登陆页
    @POST(Interface.LOGIN_INTERFACE)
    Call<ResponseBody> goLogin(@Field("username") String username,@Field("password") String password);



}
