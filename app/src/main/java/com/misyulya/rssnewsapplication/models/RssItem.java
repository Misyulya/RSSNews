package com.misyulya.rssnewsapplication.models;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssItem implements Serializable {

    @SerializedName("nameRU")
    private String mTitle;

    @SerializedName("genre")
    private String mLink;

    private int mId;
    private ImageView mImage;

//    public RssItem(String title, String description) {
//        this.mTitle = title;
//        this.mLink = description;
//    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public String getLink() {
        return mLink;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setImage(ImageView image) {
        this.mImage = image;
    }

    public ImageView getImage() {
        return mImage;
    }

}
