package com.wangrui.wan.wanandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangrui.wan.wanandroid.GetRequest;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.activity.ArticleDetailsActivity;
import com.wangrui.wan.wanandroid.adapter.ArticleClassifyAdapter;
import com.wangrui.wan.wanandroid.bean.ArticleItemBean;
import com.wangrui.wan.wanandroid.bean.KnowLeageArticleBean;
import com.wangrui.wan.wanandroid.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPagerFragment extends Fragment {

    private RecyclerView recyclerView;
    private int id;
    private int tag;
    private ArrayList<ArticleItemBean> list;
    private ArticleClassifyAdapter myRecycleAdapter;

    public static ViewPagerFragment newInstance(int id,int tag) {
        Bundle args = new Bundle();
        args.putInt("id",id);
        args.putInt("tag",tag);
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        id = getArguments().getInt("id");
        tag = getArguments().getInt("tag");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_pager_fragment_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.viewPager_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        if (tag == 0) {
            getData();
        } else if (tag == 1) {
            getData(1);
        }
    }
    private void initAdapter() {
        myRecycleAdapter = new ArticleClassifyAdapter(list);
        recyclerView.setAdapter(myRecycleAdapter);
        myRecycleAdapter.setOnItemClickListener(new ArticleClassifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String data, String title) {
                Intent intent = new Intent(getActivity(),ArticleDetailsActivity.class);
                intent.putExtra("link",data);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }

    private void getData(int tag) {
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
        Call<KnowLeageArticleBean> call = request.getGZArticle(id,0);
        call.enqueue(new Callback<KnowLeageArticleBean>() {
            @Override
            public void onResponse(Call<KnowLeageArticleBean> call, Response<KnowLeageArticleBean> response) {
                List<KnowLeageArticleBean.DataBean.DatasBean> datasBeanList = response.body().getData().getDatas();
                for (int i = 0; i<datasBeanList.size();i++) {
                    ArticleItemBean itemBean = new ArticleItemBean();
                    itemBean.setUrl(datasBeanList.get(i).getLink());
                    itemBean.setAuthor(datasBeanList.get(i).getAuthor());
                    itemBean.setTitle(datasBeanList.get(i).getTitle());
                    itemBean.setData(datasBeanList.get(i).getNiceDate());
                    itemBean.setChapter(datasBeanList.get(i).getChapterName());
                    list.add(itemBean);
                }
                initAdapter();
            }

            @Override
            public void onFailure(Call<KnowLeageArticleBean> call, Throwable throwable) {

            }
        });
    }

    private void getData() {
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
        Call<KnowLeageArticleBean> call = request.getKnowLeageArticle(id);
        call.enqueue(new Callback<KnowLeageArticleBean>() {
            @Override
            public void onResponse(Call<KnowLeageArticleBean> call, Response<KnowLeageArticleBean> response) {
                List<KnowLeageArticleBean.DataBean.DatasBean> datasBeanList = response.body().getData().getDatas();
                for (int i = 0; i<datasBeanList.size();i++) {
                    ArticleItemBean itemBean = new ArticleItemBean();
                    itemBean.setUrl(datasBeanList.get(i).getLink());
                    itemBean.setAuthor(datasBeanList.get(i).getAuthor());
                    itemBean.setTitle(datasBeanList.get(i).getTitle());
                    itemBean.setData(datasBeanList.get(i).getNiceDate());
                    itemBean.setChapter(datasBeanList.get(i).getChapterName());
                    list.add(itemBean);
                }
                initAdapter();
            }

            @Override
            public void onFailure(Call<KnowLeageArticleBean> call, Throwable throwable) {

            }
        });
    }
}
