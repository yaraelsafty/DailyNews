package com.example.yara.dailynews.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yara on 02-Feb-19.
 */

public class ApiClient {
    public static final String BASE_URL = "https://newsapi.org/";
    public static final String API_Key ="a8d4f0a395d7431e8da1b681346f23d2";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
