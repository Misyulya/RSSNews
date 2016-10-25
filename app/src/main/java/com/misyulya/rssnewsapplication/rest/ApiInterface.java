package com.misyulya.rssnewsapplication.rest;


import com.misyulya.rssnewsapplication.models.RssResponse;

import retrofit2.Call;
import retrofit2.http.GET;

//import com.misyulya.lesson11_json.models.Review;

/**
 * Created by 1 on 14.04.2016.
 */
public interface ApiInterface {
    @GET(ApiRss.RSS)
    Call<RssResponse> getRSS();
//
//    @GET(ApiFilms.DETAILS)
//    Call<Review> getReviewDescription(@Path("reviewID") int id);
}