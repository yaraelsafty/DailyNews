package com.example.yara.dailynews.DataBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Yara on 05-Feb-19.
 */

public class NewsRepository {
    private static String TAG;
    private NewsDao newsDao;
    private LiveData<List<NewsEntry>> mAllNews;


    public NewsRepository(Application application) {
        AppDatabase db = AppDatabase.getsInstance(application);
        newsDao = db.newsDao();
        mAllNews = newsDao.loadAllNews();
    }

    public LiveData<List<NewsEntry>> getAllMovies() {
        return mAllNews;
    }

    public void insert(NewsEntry news) {
        new insertAsyncTask(newsDao).execute(news);
    }



    /**
     * Insert a word into the database.
     */
    private static class insertAsyncTask extends AsyncTask<NewsEntry, Void, Void> {

        private NewsDao mAsyncTaskDao;

        insertAsyncTask(NewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NewsEntry... params) {
            mAsyncTaskDao.insertNews(params[0]);
            Log.d(TAG,""+params[0]);
            return null;
        }
    }
}
