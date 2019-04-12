package com.example.commicbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commicbook.Common.Common;
import com.example.commicbook.Interface.IRecyclerViewOnClick;
import com.example.commicbook.Model.Chapter;
import com.example.commicbook.R;
import com.example.commicbook.ViewDetailsActivity;

import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder> {
    Context context;
    List<Chapter> chapterList;

    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.textView.setText(new StringBuilder(chapterList.get(position).Name));
        Common.selected_chapeter = chapterList.get(position);
        Common.chapter_index = position;
        myViewHolder.setiRecyclerViewOnClick(new IRecyclerViewOnClick() {
            @Override
            public void onClick(View view, int position) {
                Common.selected_chapeter = chapterList.get(position);
                Common.chapter_index = position;
                context.startActivity(new Intent(context, ViewDetailsActivity.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        IRecyclerViewOnClick iRecyclerViewOnClick;
        TextView textView;


        public void setiRecyclerViewOnClick(IRecyclerViewOnClick iRecyclerViewOnClick) {
            this.iRecyclerViewOnClick = iRecyclerViewOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chapter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerViewOnClick.onClick(v, getAdapterPosition());
        }
    }
}
