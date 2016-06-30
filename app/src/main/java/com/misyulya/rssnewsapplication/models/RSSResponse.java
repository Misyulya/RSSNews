package com.misyulya.rssnewsapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1 on 30.06.2016.
 */
public class RSSResponse {

    @SerializedName("RSSData")
    private ArrayList<RSS> mRSSData;

    public ArrayList<RSS> getRSSData() {
        return mRSSData;
    }
}
