package com.misyulya.rssnewsapplication.models;

import android.os.Parcelable;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssItem implements Serializable {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    private int mId;
    private ImageView mImage;

//    public RssItem(String title, String description) {
//        this.mTitle = title;
//        this.mDescription = description;
//    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDescription() {
        return mDescription;
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
