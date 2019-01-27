package com.wangrui.wan.wanandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wangrui.wan.wanandroid.BannerDate;
import com.wangrui.wan.wanandroid.GetRequest;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.adapter.MyRecycleAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private RecyclerView mRecycleView;
    private MyRecycleAdapter myRecycleAdapter;
    private ArrayList<String> list;
    private NavigationView navigationView;
    private Banner banner;
    private List<BannerDate.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        initBanner();

        getDate();
    }

    private void initView() {
        banner = findViewById(R.id.banner);
        navigationView = (NavigationView) findViewById(R.id.menu_view);
        mRecycleView = findViewById(R.id.recycle);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list = initList();
        myRecycleAdapter = new MyRecycleAdapter(list);

        mRecycleView.setAdapter(myRecycleAdapter);

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


    }

    private ArrayList<String> initList () {
        list = new ArrayList<>();
        for (int i = 0; i <= 20; i ++ ) {
            list.add(""+i);
        }
        return list;
    }

    private void getDate() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        GetRequest request = retrofit.create(GetRequest.class);
        Call<BannerDate> call = request.getBannerDate();
        call.enqueue(new Callback<BannerDate>() {
            @Override
            public void onResponse(Call<BannerDate> call, Response<BannerDate> response) {
                Log.i("wangrui666","数据" + response.body().getData());
                data =  response.body().getData();
                List<String> images = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (int i = 0;i < data.size(); i++) {
                    Log.i("wangrui666","title"+data.get(i).getImagePath());
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
                Toast.makeText(MainActivity.this,"链接失败！", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public class GildeImageLoader extends  ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(MainActivity.this).load(path).into(imageView);
        }
    }
}
