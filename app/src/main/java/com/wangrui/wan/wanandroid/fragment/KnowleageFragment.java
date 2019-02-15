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
import com.wangrui.wan.wanandroid.activity.AriticleClassifyActivity;
import com.wangrui.wan.wanandroid.adapter.KnowleageRecycleAdapter;
import com.wangrui.wan.wanandroid.bean.KnowleageBean;
import com.wangrui.wan.wanandroid.bean.KnowleageEntry;
import com.wangrui.wan.wanandroid.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KnowleageFragment extends Fragment {

    private RecyclerView recyclerView;
    private KnowleageRecycleAdapter adapter;
    private ArrayList<String> childrenName = new ArrayList<>();
    private ArrayList<Integer> childrenId = new ArrayList<>();
    private ArrayList<KnowleageEntry> knowleageEntries = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowleage_fragment_layout,container,false);
        initList();
        initView(view);

        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.knowleage_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private ArrayList<KnowleageEntry> initList(){
        getData();
        return knowleageEntries;
    }


    private void getData() {
        GetRequest request = RetrofitUtils.getInstance().create(GetRequest.class);
        Call<KnowleageBean> call = request.getArticleList();
        call.enqueue(new Callback<KnowleageBean>() {
            @Override
            public void onResponse(Call<KnowleageBean> call, Response<KnowleageBean> response) {
                List<KnowleageBean.DataBean> dataBeans = response.body().getData();
                List<KnowleageBean.DataBean.ChildrenBean> childrenBeans;
                for (int i= 0;i<dataBeans.size();i++) {
                    KnowleageEntry entry = new KnowleageEntry();
                    entry.setName(dataBeans.get(i).getName());
                    childrenBeans = dataBeans.get(i).getChildren();
                    ArrayList<KnowleageEntry.ChildrenEntry> childrenEntries = new ArrayList<>();
                    for(int j = 0; j<childrenBeans.size();j++) {
                        KnowleageEntry.ChildrenEntry data = new KnowleageEntry.ChildrenEntry();
                        data.setChildrenName(childrenBeans.get(j).getName());
                        data.setChildrenId(childrenBeans.get(j).getId());
                        childrenEntries.add(data);
                    }
                    entry.setChildrenEntries(childrenEntries);
                    knowleageEntries.add(entry);
                }

                adapter = new KnowleageRecycleAdapter(knowleageEntries);
                recyclerView.setAdapter(adapter);
                initAdapter(adapter);

            }

            @Override
            public void onFailure(Call<KnowleageBean> call, Throwable throwable) {

            }
        });

    }

    private void initAdapter(KnowleageRecycleAdapter adapter){
        adapter.setOnItemClickListener(new KnowleageRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ArrayList<String> title, ArrayList<Integer> id, String name) {
                Intent intent = new Intent(getActivity(),AriticleClassifyActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("titles",title);
                intent.putExtra("ids",id);
                startActivity(intent);
            }
        });
    }
}
