package com.example.yara.dailynews.UI;


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.yara.dailynews.Adapter.newsAdapter;
import com.example.yara.dailynews.Data.Article;
import com.example.yara.dailynews.Data.News;
import com.example.yara.dailynews.R;
import com.example.yara.dailynews.Utils.ApiClient;
import com.example.yara.dailynews.Utils.NewsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private String TAG=NewsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Parcelable savedRecyclerViewState;
    private RecyclerView.LayoutManager mlayoutManager;
    private final String LIST_STATE = "recycler_state";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COUNTRY = "country";
    private static final String ARG_CATEGORY= "category";

    // TODO: Rename and change types of parameters
    private String country;
    private String category;


    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY, param1);
        args.putString(ARG_CATEGORY, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = getArguments().getString(ARG_COUNTRY);
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            savedRecyclerViewState = savedInstanceState.getParcelable(LIST_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_news, container, false);
        progressBar=view.findViewById(R.id.progressBar);
        recyclerView=view.findViewById(R.id.rv_news);
        mlayoutManager=new LinearLayoutManager(this.getActivity());


//        if (savedInstanceState != null) {
//            savedRecyclerViewState = savedInstanceState.getParcelable(LIST_STATE);
//            Log.d(TAG,"***get state"+savedRecyclerViewState);
//
//        }


        getData();
        return view;
    }

    private void getData() {
        NewsService apiService = ApiClient.getClient().create(NewsService.class);
        Call<News> call = apiService.getNewsCategory(country,category,ApiClient.API_Key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                progressBar.setVisibility(View.GONE);
                List<Article> news=response.body().getArticles();
                recyclerView.setAdapter(new newsAdapter(getActivity(),news));

                if (savedRecyclerViewState != null) {
                    Log.d(TAG,"***"+savedRecyclerViewState);
                    recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
                }
                else {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        recyclerView.setLayoutManager(mlayoutManager);
                        recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
                   }
                   else {
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
                        recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);

                    }
                }
                recyclerView.setLayoutManager(mlayoutManager);
                Log.d(TAG,""+savedRecyclerViewState);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG,t.getMessage());

            }
        });
}

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        savedRecyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE, savedRecyclerViewState);
    }


    @Override
    public void onPause() {
        super.onPause();
        savedRecyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (savedRecyclerViewState !=null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
        }
    }

}
