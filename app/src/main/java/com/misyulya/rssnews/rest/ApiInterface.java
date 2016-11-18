package com.misyulya.rssnews.rest;


import com.misyulya.rssnews.model.RssResponse;

import retrofit2.Call;
import retrofit2.http.GET;

//import com.misyulya.lesson11_json.models.Review;

/**
 * Created by 1 on 14.04.2016.
 */
public interface ApiInterface {
    @GET(ApiRss.RSS)
    Call<RssResponse> getRSS();
}