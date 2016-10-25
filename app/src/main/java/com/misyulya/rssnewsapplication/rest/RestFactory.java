package com.misyulya.rssnewsapplication.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1 on 14.04.2016.
 */
public class RestFactory {
    public static ApiInterface get(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiFilms.KINOPOISK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        return service;
    }
}
