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

public class ArticleClassifyAdapter extends RecyclerView.Adapter<ArticleClassifyAdapter.ViewHolder> {

    private ArrayList<ArticleItemBean> list;

    public ArticleClassifyAdapter(ArrayList<ArticleItemBean> list) {
        this.list = list;
    }

    public OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String data,String title);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycle_view_item,viewGroup,false);
        ArticleClassifyAdapter.ViewHolder holder = new ArticleClassifyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final String title = list.get(i).getTitle();
        String chapter = list.get(i).getChapter();
        String author = list.get(i).getAuthor();
        String date = list.get(i).getData();
        final String link = list.get(i).getUrl();
        viewHolder.title.setText(title);
        viewHolder.chapter.setText(chapter);
        viewHolder.author.setText(author);
        viewHolder.date.setText(date);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(i,link,title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,date,author,chapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycle_item);
            date = itemView.findViewById(R.id.article_data);
            author = itemView.findViewById(R.id.article_author);
            chapter = itemView.findViewById(R.id.article_chapter);
        }
    }
}
