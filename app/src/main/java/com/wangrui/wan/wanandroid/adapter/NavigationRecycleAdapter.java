package com.wangrui.wan.wanandroid.adapter;

import android.app.ActivityOptions;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.bean.NavigationBean;
import com.wangrui.wan.wanandroid.utils.CommonUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class NavigationRecycleAdapter extends BaseQuickAdapter<NavigationBean.DataBean,NavigationRecycleAdapter.ViewHolder> {

    private List<NavigationBean.DataBean> data;

    public NavigationRecycleAdapter(int layoutResId, @Nullable List<NavigationBean.DataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(ViewHolder helper, NavigationBean.DataBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.navigation_item_tv, item.getName());
        }
        final TagFlowLayout mTagFlowLayout = helper.getView(R.id.navigation_tag);
        List<NavigationBean.DataBean.ArticlesBean> articlesBeanList = item.getArticles();
        mTagFlowLayout.setAdapter(new TagAdapter<NavigationBean.DataBean.ArticlesBean>(articlesBeanList) {
            @Override
            public View getView(FlowLayout parent, int position, NavigationBean.DataBean.ArticlesBean articlesBean) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                        mTagFlowLayout, false);

                if (articlesBean == null) {
                    return null;
                }
                String name = articlesBean.getTitle();
                tv.setPadding(CommonUtils.dp2px(10), CommonUtils.dp2px(10),
                        CommonUtils.dp2px(10), CommonUtils.dp2px(10));
                tv.setText(name);
                tv.setTextColor(CommonUtils.randomColor());
                mTagFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    //startNavigationPager(view, position1, parent, mArticles);
                    return true;
                });
                return tv;
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startNavigationPager(View view, int position1, FlowLayout parent2, List<NavigationBean.DataBean.ArticlesBean> mArticles) {
        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view,
                view.getWidth() / 2,
                view.getHeight() / 2,
                0 ,
                0);
//        JudgeUtils.startArticleDetailActivity(parent2.getContext(),
//                options,
//                mArticles.get(position1).getId(),
//                mArticles.get(position1).getTitle(),
//                mArticles.get(position1).getLink(),
//                mArticles.get(position1).isCollect(),
//                false,
//                false);
    }


    class ViewHolder extends BaseViewHolder {

        TextView textView;
        FlowLayout flowLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.navigation_item_tv);
            flowLayout = itemView.findViewById(R.id.navigation_tag);
        }
    }
}
