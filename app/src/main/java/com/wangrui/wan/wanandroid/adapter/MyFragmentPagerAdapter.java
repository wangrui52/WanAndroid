package com.wangrui.wan.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.fragment.ViewPagerFragment;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;
    private Context mContext;
    private ArrayList<Integer> ids;
    private int tag;


    public MyFragmentPagerAdapter(Context context,FragmentManager fm, ArrayList<String> titles, ArrayList<Integer> ids,int tag) {
        super(fm);
        this.titles = titles;
        this.ids = ids;
        this.tag = tag;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        return ViewPagerFragment.newInstance(ids.get(i),tag);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item, null);
        TextView tv= (TextView) view.findViewById(R.id.tab_title);
        tv.setText(titles.get(position));
        return view;
    }

    public View getSelectTabVeiw(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_select_item,null);
        TextView tv= (TextView) view.findViewById(R.id.tab_title);
        tv.setText(titles.get(position));
        return view;
    }


}
