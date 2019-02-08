package com.example.yara.dailynews.DataBase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Yara on 05-Feb-19.
 */

public class MainViewModel extends AndroidViewModel {
    LiveData<List<NewsEntry>> list;
    private NewsRepository mRepository;
    String TAG = this.getClass().getSimpleName();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        list = mRepository.getAllMovies();
    }

    public LiveData<List<NewsEntry>> getAllNews() {
        return list;
    }


    public void insert(NewsEntry newsEntry) {
        mRepository.insert(newsEntry);
    }


}
