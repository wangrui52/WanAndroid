package com.wangrui.wan.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.bean.ArticleItemBean;

import java.util.ArrayList;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.ViewHolder> {

    private ArrayList<ArticleItemBean> list;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public MyRecycleAdapter(ArrayList<ArticleItemBean> list) {
        this.list = list;
    }


    private View mHeaderView;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    interface OnItemClickListener {
        void onItemClick(int position, String data);
    }

    public void addDatas(ArrayList<ArticleItemBean> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mHeaderView != null && i == TYPE_HEADER)
            return new ViewHolder(mHeaderView);
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycle_view_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleAdapter.ViewHolder viewHolder, int i) {

        if(getItemViewType(i) == TYPE_HEADER)
            return;
        final int pos = getRealPosition(viewHolder);
        final String title = list.get(pos).getTitle();
        String chapter = list.get(pos).getChapter();
        String author = list.get(pos).getAuthor();
        String date = list.get(pos).getData();
        viewHolder.title.setText(title);
        viewHolder.chapter.setText(chapter);
        viewHolder.author.setText(author);
        viewHolder.date.setText(date);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null)
            return TYPE_NORMAL;
        if(position == 0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? list.size() : list.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,date,author,chapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if(itemView == mHeaderView)
                return;
            title = itemView.findViewById(R.id.recycle_item);
            date = itemView.findViewById(R.id.article_data);
            author = itemView.findViewById(R.id.article_author);
            chapter = itemView.findViewById(R.id.article_chapter);
        }
    }
}
