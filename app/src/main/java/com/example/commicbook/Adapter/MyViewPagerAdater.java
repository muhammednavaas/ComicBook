package com.example.commicbook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commicbook.Model.Link;
import com.example.commicbook.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyViewPagerAdater extends PagerAdapter {
    Context context;
    List<Link> linkList;
    LayoutInflater inflater;

    public MyViewPagerAdater(Context context, List<Link> linkList) {
        this.context = context;
        this.linkList = linkList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return linkList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View image_Layout = inflater.inflate(R.layout.view_pager_item, container, false);
        PhotoView page_Image = image_Layout.findViewById(R.id.image_pager);
        Picasso.get().load(linkList.get(position).getLink()).into(page_Image);

        container.addView(image_Layout);
        return image_Layout;
    }
}
