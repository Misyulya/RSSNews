package com.misyulya.rssnewsapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 1 on 30.06.2016.
 */
public class RssResponse {

    @SerializedName("articles")
    private List<RssItem> rssItems;

    public List<RssItem> getRssData() {
        return rssItems;
    }
}
