package com.wangrui.wan.wanandroid.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wangrui.wan.wanandroid.bean.BannerDate;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.fragment.GongzhongFragment;
import com.wangrui.wan.wanandroid.fragment.HomeFragment;
import com.wangrui.wan.wanandroid.fragment.KnowleageFragment;
import com.wangrui.wan.wanandroid.fragment.NavigationFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private List<BannerDate.DataBean> data;
    private HomeFragment homeFragment;
    private GongzhongFragment gongzhongFragment;
    private KnowleageFragment knowleageFragment;
    private NavigationFragment navigationFragment;
    private TextView title;
    private int lastShowFragment = 0;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.home:
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    title.setText("首页");
                    return true;
                case R.id.know:
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    title.setText("知识体系");
                    return true;
                case R.id.gongzhonghao:
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    title.setText("公众号");
                    return true;
                case R.id.navigation:
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment, 3);
                        lastShowFragment = 3;
                    }
                    title.setText("导航");
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


    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.menu_view);
        bottomNavigationView = findViewById(R.id.Bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        title = findViewById(R.id.toolbar_title);

        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}
