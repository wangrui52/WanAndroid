package com.wangrui.wan.wanandroid.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

public class AriticleClassifyActivity extends AppCompatActivity {

    private TextView name;
    private String titleName;
    private ArrayList<String> titles;
    private ArrayList<Integer> ids;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariticle_classify);
        Intent intent = getIntent();
        titleName = intent.getStringExtra("name");
        titles = intent.getStringArrayListExtra("titles");
        ids = intent.getIntegerArrayListExtra("ids");

        name = findViewById(R.id.toolbar_name);
        name.setText(titleName);
        initView();
    }

    private void initView() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.framePage);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager()
                ,titles,ids);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
