package com.example.commicbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commicbook.ChapterActivity;
import com.example.commicbook.Common.Common;
import com.example.commicbook.Interface.IRecyclerViewOnClick;
import com.example.commicbook.Model.Comic;
import com.example.commicbook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicAdapter extends RecyclerView.Adapter<MyComicAdapter.MyViewHolder> {
    private Context context;
    private List<Comic> comicsList;

    public MyComicAdapter(Context context, List<Comic> comicsList) {
        this.context = context;
        this.comicsList = comicsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comic_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Picasso.get().load(comicsList.get(position).getImage()).into(myViewHolder.imageView);
        myViewHolder.textView.setText(comicsList.get(position).getName());
        myViewHolder.setiRecyclerViewOnClick(new IRecyclerViewOnClick() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view, int position) {
                Common.selected_comic = comicsList.get(position);
                context.startActivity(new Intent(context, ChapterActivity.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        IRecyclerViewOnClick iRecyclerViewOnClick;

        public void setiRecyclerViewOnClick(IRecyclerViewOnClick iRecyclerViewOnClick) {
            this.iRecyclerViewOnClick = iRecyclerViewOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.comicImage);
            textView = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerViewOnClick.onClick(v, getAdapterPosition());
        }
    }
}
