package com.wangrui.wan.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangrui.wan.wanandroid.GetRequest;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.adapter.MyFragmentPagerAdapter;
import com.wangrui.wan.wanandroid.bean.WXArticleAuthorBean;
import com.wangrui.wan.wanandroid.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GongzhongFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<WXArticleAuthorBean.DataBean> dataBeans;
    private ArrayList<String> names;
    private ArrayList<Integer> ids;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gongzhong_fragmnet_layout,container,false);
        getTabDate(view);
        return view;
    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.framePage);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(0);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity(),getChildFragmentManager()
                ,names,ids,1);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        for (int i = 0; i< tabLayout.getTabCount();i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
//                if (tab.getCustomView() != null) {
//
//                }
            }
        }

    }

    private void getTabDate(final View view) {
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
        Call<WXArticleAuthorBean> call = request.getWXArticle();
        call.enqueue(new Callback<WXArticleAuthorBean>() {
            @Override
            public void onResponse(Call<WXArticleAuthorBean> call, Response<WXArticleAuthorBean> response) {
                names = new ArrayList<>();
                ids = new ArrayList<>();
                dataBeans = response.body().getData();
                for (int i = 0; i< dataBeans.size();i++) {
                    names.add(dataBeans.get(i).getName());
                    ids.add(dataBeans.get(i).getId());
                }
                initView(view);

            }

            @Override
            public void onFailure(Call<WXArticleAuthorBean> call, Throwable throwable) {

            }
        });
    }
}
