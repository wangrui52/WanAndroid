package com.wangrui.wan.wanandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wangrui.wan.wanandroid.BannerDate;
import com.wangrui.wan.wanandroid.GetRequest;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.adapter.MyRecycleAdapter;
import com.wangrui.wan.wanandroid.fragment.GongzhongFragment;
import com.wangrui.wan.wanandroid.fragment.HomeFragment;
import com.wangrui.wan.wanandroid.fragment.KnowleageFragment;
import com.wangrui.wan.wanandroid.fragment.NavigationFragment;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private MyRecycleAdapter myRecycleAdapter;
    private ArrayList<String> list;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Banner banner;
    private List<BannerDate.DataBean> data;
    private HomeFragment homeFragment;
    private GongzhongFragment gongzhongFragment;
    private KnowleageFragment knowleageFragment;
    private NavigationFragment navigationFragment;
    private FragmentTransaction transaction;
    private int lastShowFragment = 0;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initBanner();
        initFragment();
        getDate();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            //hideAllFragment(transaction);

            switch (menuItem.getItemId()) {
                case R.id.home:
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }

                    return true;
                case R.id.know:
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }

                    return true;
                case R.id.gongzhonghao:
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }

                    return true;
                case R.id.navigation:
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment, 3);
                        lastShowFragment = 3;
                    }

                    return true;

            }

            return false;
        }
    };

    private void initFragment(){
        homeFragment = new HomeFragment();
        gongzhongFragment = new GongzhongFragment();
        knowleageFragment = new KnowleageFragment();
        navigationFragment = new NavigationFragment();
        fragments = new Fragment[]{homeFragment, knowleageFragment,gongzhongFragment,navigationFragment};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.framePage, homeFragment)
                .show(homeFragment)
                .commit();
    }

    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.framePage, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(homeFragment!=null){
            transaction.hide(homeFragment);
        }
        if(gongzhongFragment!=null){
            transaction.hide(gongzhongFragment);
        }
        if(knowleageFragment!=null){
            transaction.hide(knowleageFragment);
        }
        if (navigationFragment != null) {
            transaction.hide(navigationFragment);
        }
    }


    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.menu_view);
        bottomNavigationView = findViewById(R.id.Bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        mRecycleView = findViewById(R.id.recycle);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list = initList();
        myRecycleAdapter = new MyRecycleAdapter(list);
        setHeader(mRecycleView);

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
        //myRecycleAdapter.setHeaderView(banner);
    }

    private void setHeader(RecyclerView view) {
        View view1 = LayoutInflater.from(this).inflate(R.layout.recycle_banner_header,view,false);
        banner = view1.findViewById(R.id.banner);
        myRecycleAdapter.setHeaderView(view1);
    }

    private ArrayList<String> initList () {
        list = new ArrayList<>();
        for (int i = 0; i <= 20; i ++ ) {
            list.add(""+i);
        }
        return list;
    }

    private void getDate() {
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
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
