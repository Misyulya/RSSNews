package com.misyulya.rssnewsapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssItem implements Serializable {

    @SerializedName("author")
    private String mTitle;

    @SerializedName("title")
    private String mGenre;

    @SerializedName("urlToImage")
    private String mPosterURL;

    private transient int mId;

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setGenre(String genre) {
        this.mGenre = genre;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setPosterURL(String posterURL) {
        this.mPosterURL = posterURL;
    }

    public String getPosterURL() {
        return mPosterURL;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }
}
