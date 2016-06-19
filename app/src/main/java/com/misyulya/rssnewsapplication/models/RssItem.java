package com.misyulya.rssnewsapplication.models;

import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssItem implements Serializable{

    private String mTitle;
    private String mDescription;
    private ImageView mImage;

    public RssItem(String title, String description) {
        this.mTitle = title;
        this.mDescription = description;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String title) {
        this.mTitle = title;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String description) {
        this.mDescription = description;
    }

    public ImageView getmImage() {
        return mImage;
    }

    public void setmImage(ImageView image) {
        this.mImage = image;
    }
}
