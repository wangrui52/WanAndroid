package com.wangrui.wan.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wangrui.wan.wanandroid.GetRequest;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.adapter.NavigationRecycleAdapter;
import com.wangrui.wan.wanandroid.bean.NavigationBean;
import com.wangrui.wan.wanandroid.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationFragment extends Fragment {
    private ArrayList<NavigationBean.DataBean.ArticlesBean> list;
    private ArrayList<String> tabList;
    private VerticalTabLayout verticalTabLayout;
    private RecyclerView recyclerView;
    private NavigationRecycleAdapter mNavigationAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment_layout,container,false);
        getNavigation(view);
        return view;
    }

    private void getNavigation(final View view) {
        list = new ArrayList<>();
        tabList = new ArrayList<>();
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
        Call<NavigationBean> call = request.getNavigationDate();
        call.enqueue(new Callback<NavigationBean>() {
            @Override
            public void onResponse(Call<NavigationBean> call, Response<NavigationBean> response) {
                List<NavigationBean.DataBean> date = response.body().getData();
                List<NavigationBean.DataBean.ArticlesBean> articlesBeanList;
                String name;
                for (int i = 0;i< date.size();i++) {
                    name = date.get(i).getName();
                    tabList.add(name);
                }
                initView(view,tabList,date);

            }

            @Override
            public void onFailure(Call<NavigationBean> call, Throwable throwable) {

            }
        });
    }

    private void initView(View view, final ArrayList tabList,List<NavigationBean.DataBean> data) {
        verticalTabLayout = view.findViewById(R.id.vertical_tab);
        recyclerView = view.findViewById(R.id.navigation_recycle);
        List<NavigationBean.DataBean> navigationDataList = new ArrayList<>();
        mNavigationAdapter = new NavigationRecycleAdapter(R.layout.navigation_recycle_item, data);
        LinearLayoutManager mManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mNavigationAdapter);
        mNavigationAdapter.replaceData(data);


        verticalTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return tabList == null ? 0 : tabList.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent((String) tabList.get(position))
                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.shallow_green),
                                ContextCompat.getColor(getActivity(), R.color.shallow_grey))
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });
    }
}
