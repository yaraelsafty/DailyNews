package com.example.yara.dailynews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yara.dailynews.Data.Article;
import com.example.yara.dailynews.MainActivity;
import com.example.yara.dailynews.R;
import com.example.yara.dailynews.UI.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.security.acl.LastOwnerException;
import java.util.List;

/**
 * Created by Yara on 02-Feb-19.
 */

public class newsAdapter extends RecyclerView.Adapter<newsVH> {
    private String TAG=newsAdapter.class.getSimpleName();
    private Context context;
    private List<Article> list;

    public newsAdapter(Context context, List<Article> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public newsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new newsVH(view);
    }

    @Override
    public void onBindViewHolder(newsVH holder, int position) {
        final Article article=list.get(position);
        holder.title.setText(article.getTitle());
        Picasso.with(context).load(article.getUrlToImage()).placeholder(R.drawable.ic_no_photo_available).into(holder.newsImage);
        Log.d(TAG,""+article.getSource().getName() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsFragment fragment=new DetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("title",article.getTitle());
                bundle.putString("image",article.getUrlToImage());
                bundle.putString("time",article.getPublishedAt());
                bundle.putString("source",article.getSource().getName());
                bundle.putString("description",article.getDescription());
                bundle.putString("url",article.getUrl());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager=((FragmentActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame,fragment ).addToBackStack( "tag" ).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
