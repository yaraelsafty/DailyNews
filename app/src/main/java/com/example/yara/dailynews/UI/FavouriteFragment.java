package com.example.yara.dailynews.UI;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yara.dailynews.DataBase.AppDatabase;
import com.example.yara.dailynews.DataBase.MainViewModel;
import com.example.yara.dailynews.DataBase.NewsDBAapter;
import com.example.yara.dailynews.DataBase.NewsEntry;
import com.example.yara.dailynews.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    private RecyclerView rv_news;
    private NewsEntry newsEntry;
    private String TAG=FavouriteFragment.class.getSimpleName();



    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite, container, false);
        rv_news=view.findViewById(R.id.rv_favorite_news);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv_news.setLayoutManager(mLayoutManager);
        getNews();
        return view;

    }

    private void getNews() {
        MainViewModel viewModel= ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getAllNews().observe(getActivity(), new Observer<List<NewsEntry>>() {

            @Override
            public void onChanged(@Nullable List<NewsEntry> Entries) {
                Log.d(TAG," updating from ViewModel  "+Entries.size());
                NewsDBAapter adapter = new NewsDBAapter(getContext(),Entries);

                rv_news.setAdapter(adapter);
            }
        });
    }

}
