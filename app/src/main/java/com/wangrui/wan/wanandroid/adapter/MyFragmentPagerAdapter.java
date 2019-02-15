package com.wangrui.wan.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wangrui.wan.wanandroid.fragment.ViewPagerFragment;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;
    private Context mContext;
    private ArrayList<Integer> ids;


    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<Integer> ids) {
        super(fm);
        this.titles = titles;
        this.ids = ids;
    }

    @Override
    public Fragment getItem(int i) {
        return ViewPagerFragment.newInstance(ids.get(i));
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
