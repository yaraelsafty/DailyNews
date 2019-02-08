package com.example.yara.dailynews.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yara.dailynews.R;

/**
 * Created by Yara on 02-Feb-19.
 */

public class newsVH extends RecyclerView.ViewHolder  {
        TextView title;
        ImageView newsImage;

        public newsVH(View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.news_title);
        newsImage=itemView.findViewById(R.id.news_image);
        }
}
