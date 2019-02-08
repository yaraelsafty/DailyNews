package com.example.yara.dailynews;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.yara.dailynews.DataBase.AppDatabase;
import com.example.yara.dailynews.DataBase.MainViewModel;
import com.example.yara.dailynews.DataBase.NewsDBAapter;
import com.example.yara.dailynews.DataBase.NewsEntry;
import com.example.yara.dailynews.UI.LoginActivity;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class NewsAppWidget extends AppWidgetProvider {
    public static String TAG=NewsAppWidget.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String title,String time) {



        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_app_widget);


        views.setTextViewText(R.id.widget_news_title, title);
        views.setTextViewText(R.id.widget_news_time, time);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String title = sharedPreferences.getString("title", null);
        String time = sharedPreferences.getString("time", null);
        Log.d(TAG,title+time);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,title ,time );
        }
        }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

