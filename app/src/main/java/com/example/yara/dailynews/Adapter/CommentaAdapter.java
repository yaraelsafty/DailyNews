package com.example.yara.dailynews.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yara.dailynews.DataBase.NewsDBAapter;
import com.example.yara.dailynews.R;

import java.util.List;

/**
 * Created by Yara on 07-Feb-19.
 */

public class CommentaAdapter extends RecyclerView.Adapter<CommentaAdapter.MyViewHolder> {
    List<String>list;
    Context context;

    public CommentaAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.comments_item, parent, false);
        return new CommentaAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentaAdapter.MyViewHolder holder, int position) {

        String currentString = list.get(position);
        String[] separated = currentString.split("/");
        holder.tv_user.setText(separated[1]);
        holder.tv_comment.setText( separated[0]);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user ,tv_comment;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_user=itemView.findViewById(R.id.tv_user_comment);
            tv_comment=itemView.findViewById(R.id.tv_comment);

        }
    }
}
