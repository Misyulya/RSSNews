package com.misyulya.rssnewsapplication.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1 on 14.04.2016.
 */
public class RestFactory {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
    logging.(HttpLoggingInterceptor.Level);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static ApiInterface get(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRss.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        return service;
    }
}
