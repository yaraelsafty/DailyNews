package com.example.yara.dailynews.Utils;

import com.example.yara.dailynews.Data.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Yara on 02-Feb-19.
 */

public interface NewsService {

    @GET("/v2/top-headlines")
    Call<News> getNews(@Query("country") String country , @Query("apiKey") String API_KEY);

    @GET("/v2/top-headlines")
    Call<News> getNewsCategory(@Query("country") String country,@Query("category") String category,@Query("apiKey") String API_KEY);


    @GET("https://newsapi.org/v2/top-headlines")
    Call<News> getGlobalNews(@Query("sources") String sources , @Query("apiKey") String API_KEY);

}
