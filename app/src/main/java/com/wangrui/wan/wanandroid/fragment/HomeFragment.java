package com.wangrui.wan.wanandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wangrui.wan.wanandroid.activity.ArticleDetailsActivity;
import com.wangrui.wan.wanandroid.bean.ArticleItemBean;
import com.wangrui.wan.wanandroid.bean.BannerDate;
import com.wangrui.wan.wanandroid.GetRequest;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.adapter.MyRecycleAdapter;
import com.wangrui.wan.wanandroid.bean.ArticleBean;
import com.wangrui.wan.wanandroid.utils.RetrofitUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private Banner banner;
    private RecyclerView mRecycleView;
    private MyRecycleAdapter myRecycleAdapter;
    private ArrayList<ArticleItemBean> list;
    private List<BannerDate.DataBean> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout,container,false);
        initView(view);
        getBannerDate();
        return view;
    }

    private void initView(View view){
        mRecycleView = view.findViewById(R.id.recycle);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        list = initList();
        myRecycleAdapter = new MyRecycleAdapter(list);
        setHeader(mRecycleView);
        mRecycleView.setAdapter(myRecycleAdapter);
        initBanner();
        myRecycleAdapter.setOnItemClickListener(new MyRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String data,String title) {
                Intent intent = new Intent(getActivity(),ArticleDetailsActivity.class);
                intent.putExtra("link",data);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }

    private ArrayList<ArticleItemBean> initList () {
        list = new ArrayList<>();
//        for (int i = 0; i <= 20; i ++ ) {
//            list.add(""+i);
//        }
        getListDate();
        return list;
    }

    private void setHeader(RecyclerView view) {
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.recycle_banner_header,view,false);
        banner = view1.findViewById(R.id.banner);
        myRecycleAdapter.setHeaderView(view1);
    }



    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //myRecycleAdapter.setHeaderView(banner);
    }

    private void getBannerDate() {
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
        Call<BannerDate> call = request.getBannerDate();
        call.enqueue(new Callback<BannerDate>() {
            @Override
            public void onResponse(Call<BannerDate> call, Response<BannerDate> response) {
                data =  response.body().getData();
                List<String> images = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (int i = 0;i < data.size(); i++) {
                    images.add(data.get(i).getImagePath());
                    titles.add(data.get(i).getTitle());
                }
                banner.setImageLoader(new GildeImageLoader());
                banner.setImages(images);
                banner.setBannerTitles(titles);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
            }

            @Override
            public void onFailure(Call<BannerDate> call, Throwable throwable) {
                Toast.makeText(getActivity(),"链接失败！", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getListDate() {
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
        Call<ArticleBean> call = request.getArticleDate();
        call.enqueue(new Callback<ArticleBean>() {
            @Override
            public void onResponse(Call<ArticleBean> call, Response<ArticleBean> response) {
                ArticleBean.DataBean data = response.body().getData();
                List<ArticleBean.DataBean.DatasBean> beans = data.getDatas();
                for (int i = 0;i < beans.size(); i++) {
                    ArticleItemBean itemBean  = new ArticleItemBean();
                    itemBean.setTitle(beans.get(i).getTitle());
                    itemBean.setData(beans.get(i).getNiceDate());
                    itemBean.setChapter(beans.get(i).getChapterName());
                    itemBean.setAuthor(beans.get(i).getAuthor());
                    itemBean.setUrl(beans.get(i).getLink());
                    list.add(itemBean);
                }
            }

            @Override
            public void onFailure(Call<ArticleBean> call, Throwable throwable) {

            }
        });
    }

    public class GildeImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getActivity()).load(path).into(imageView);
        }
    }
}
