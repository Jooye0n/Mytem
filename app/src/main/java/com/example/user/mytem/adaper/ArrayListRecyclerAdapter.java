package com.example.user.mytem.adaper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mytem.R;
import com.example.user.mytem.singleton.Post;
import com.example.user.mytem.viewholder.ArrayListViewHolder;

import java.util.ArrayList;

public class ArrayListRecyclerAdapter extends RecyclerView.Adapter<ArrayListViewHolder> {
    private ArrayList <Post> list;
    private OnItemClickListener listener;

    public ArrayListRecyclerAdapter( ArrayList <Post> recyclerItems ) {
        this.list = recyclerItems;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 생성자 구현


    @Override
    public ArrayListViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new ArrayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ArrayListViewHolder holder, final int position) {
        // holder에 아이템 binding
        // holder의 아이템에 listener 기능 등록
        holder.bindData(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
