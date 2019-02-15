package com.wangrui.wan.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.bean.KnowleageEntry;

import java.util.ArrayList;
import java.util.List;

public class KnowleageRecycleAdapter extends RecyclerView.Adapter<KnowleageRecycleAdapter.ViewHolder>{

    private ArrayList<KnowleageEntry> list;
    private List<KnowleageEntry.ChildrenEntry> children;
    public KnowleageRecycleAdapter(ArrayList<KnowleageEntry> l) {
        this.list = l;
    }

    public OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ArrayList<String> title,ArrayList<Integer> id,String name);
    }

    @NonNull
    @Override
    public KnowleageRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.knowleage_article_tem,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KnowleageRecycleAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.childrentitle.setText("");
        final String name = list.get(i).getName();
        viewHolder.title.setText(name);
        children = list.get(i).getChildrenEntries();
        final ArrayList<String> titles = new ArrayList<>();
        final ArrayList<Integer> ids = new ArrayList<>();
        for (int n = 0; n < children.size(); n++) {
            String child = children.get(n).getChildrenName();
            int id = children.get(n).getChildrenId();
            viewHolder.childrentitle.append(child + "  ");
            titles.add(child);
            ids.add(id);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(i,titles,ids,name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView childrentitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            childrentitle = itemView.findViewById(R.id.children_title);
        }
    }
}
