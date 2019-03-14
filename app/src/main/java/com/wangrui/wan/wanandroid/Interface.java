package com.wangrui.wan.wanandroid;


/**
 * 封装一个接口类，
 * by wangrui
 */
public class Interface {

    //baseUrl
    public static final String BASE_URL = "http://www.wanandroid.com";

    //首页banner轮播图
    public static final String HOME_BANNER = "/banner/json";

    //首页文章
    public static final String HOME_ARTICLE = "/article/list/0/json";

    //知识体系分类列表
    public static final String KNOWLEAGE_LIST = "/tree/json";

    //知识体系分类文章
    public static final String KNOWLEAGE_LIST_ARITICLE = "/article/list/0/json";

    //公众号文章
    public static final String GONGZGONGHAO_ARITICLE = "/wxarticle/chapters/json";

    //公众号文章详情
    public static final String GONGZHONG_DETIlE = "/wxarticle/list/{chapterId}/{pageId}/json";

    //导航页
    public static final String NAVIGATION_LINK = "/navi/json";

    //登陆
    public static final String LOGIN_INTERFACE = "/user/login";


}
