package com.misyulya.rssnewsapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 1 on 30.06.2016.
 */
public class RssResponse {

    @SerializedName("filmsData")
    private List<RssItem> rssItems;

    public List<RssItem> getRssData() {
        return rssItems;
    }
}
