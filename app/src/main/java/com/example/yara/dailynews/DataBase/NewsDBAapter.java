package com.example.yara.dailynews.DataBase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yara.dailynews.Adapter.newsVH;
import com.example.yara.dailynews.Data.Article;
import com.example.yara.dailynews.R;
import com.example.yara.dailynews.UI.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Yara on 05-Feb-19.
 */

public class NewsDBAapter extends RecyclerView.Adapter<NewsDBAapter.MyViewHolder> {
    String TAG= this.getClass().getSimpleName();
    private List<NewsEntry> list;
    Context context;

    public NewsDBAapter(Context context, List<NewsEntry> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NewsDBAapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsDBAapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsDBAapter.MyViewHolder holder, int position) {
        final NewsEntry article=list.get(position);
        Log.d(TAG," "+list.size());
        holder.title.setText(article.getTitle());
        Picasso.with(context).load(article.getImageUrl()).into(holder.newsImage);
        Log.d(TAG,""+article.getResource() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsFragment fragment=new DetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("title",article.getTitle());
                bundle.putString("image",article.getImageUrl());
                bundle.putString("time",article.getTime());
                bundle.putString("source",article.getResource());
                bundle.putString("description",article.getDetails());
                bundle.putString("url",article.getImageUrl());
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView newsImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.news_title);
            newsImage=itemView.findViewById(R.id.news_image);
        }
    }
}
