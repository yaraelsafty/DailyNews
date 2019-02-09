package com.example.yara.dailynews.UI;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yara.dailynews.DataBase.AppDatabase;
import com.example.yara.dailynews.DataBase.AppExecutors;
import com.example.yara.dailynews.DataBase.MainViewModel;
import com.example.yara.dailynews.DataBase.NewsEntry;
import com.example.yara.dailynews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private String TAG=DetailsFragment.class.getSimpleName();
    private TextView tv_Title;
    private TextView tv_Description;
    private TextView tv_Time;
    private TextView tv_Source;
    private ImageView iv_newsImage;
    private ImageView iv_addcomment;
    private ImageView iv_addToWidget;
    private CheckBox ch_favorite;
    private FloatingActionButton fab;


    private String title ;
    private String description  ;
    private String time ;
    private String source ;
    private String image_url;
    private String url;

    private SharedPreferences prefs ;
    private SharedPreferences.Editor editor;

    private AppDatabase mDB;
    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details, container, false);
        tv_Title=view.findViewById(R.id.tv_news_title);
        tv_Description=view.findViewById(R.id.tv_news_details);
        tv_Time=view.findViewById(R.id.tv_time);
        tv_Source=view.findViewById(R.id.tv_source);
        iv_newsImage=view.findViewById(R.id.news_image);
        ch_favorite=view.findViewById(R.id.ch_favorite);
        iv_addcomment=view.findViewById(R.id.iv_add_Comment);
        iv_addToWidget=view.findViewById(R.id.iv_add_to_widget);
        fab = view.findViewById(R.id.fab);


        //save to widget
        prefs =getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = prefs.edit();

        mDB=AppDatabase.getsInstance(getContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            title=bundle.getString("title");
            image_url=bundle.getString("image");
            time=bundle.getString("time");
            source=bundle.getString("source");
            description=bundle.getString("description");
            url= bundle.getString("url");

        }
        IsFavorite();
        tv_Title.setText(title);
        tv_Description.setText(description);
        tv_Source.setText(source);
        tv_Time.setText(time);
        Picasso.with(getContext()).load(image_url).placeholder(R.drawable.ic_no_photo_available).into(iv_newsImage);

        ch_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch_favorite.isChecked())
                {
                    addToFavorite();
                }
                else
                    Log.d(TAG,"deleted");
                deleteFromFavorite();
            }
        });

        iv_addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTOAddComment();
            }
        });

        iv_addToWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("title",title);
                editor.putString("time",time);
                editor.commit();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLink();
            }
        });

        return view;
    }
    private void shareLink() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share link!"));
    }
    private void goTOAddComment() {
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        bundle.putString("image",image_url);
        CommentsFragment fragment=new CommentsFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager=(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame,fragment ).addToBackStack( "tag" ).commit();
    }

    private void IsFavorite() {
        ch_favorite.setChecked(false);


        MainViewModel viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllNews().observe(getActivity(), new Observer<List<NewsEntry>>() {

            @Override
            public void onChanged(@Nullable List<NewsEntry> Entries) {
                Log.d(TAG," from ViewModel  favorite list size  "+Entries.size() );
                for (int i=0; i<Entries.size(); i++){
                    if (Entries.get(i).getTitle().equals(title))
                    {
                        ch_favorite.setChecked(true);
                    }
                }
            }
        });
    }
    private void addToFavorite() {
        final NewsEntry newsEntry=new NewsEntry(title,image_url,description,time,source,url);
        MainViewModel viewModel=ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.insert(newsEntry);

    }
    private void deleteFromFavorite() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDB.newsDao().deleteNews(title);

            }
        });

    }



}
