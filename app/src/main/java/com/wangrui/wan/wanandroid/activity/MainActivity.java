package com.wangrui.wan.wanandroid.activity;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.adapter.MyRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView mRecycleView;
    private MyRecycleAdapter myRecycleAdapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.menu_view);
        mRecycleView = findViewById(R.id.recycle);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list = initList();
        myRecycleAdapter = new MyRecycleAdapter(list);
        mRecycleView.setAdapter(myRecycleAdapter);
    }

    private ArrayList<String> initList () {
        list = new ArrayList<>();
        for (int i = 0; i <= 20; i ++ ) {
            list.add(""+i);
        }
        return list;
    }
}
