package com.misyulya.rssnewsapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1 on 30.06.2016.
 */

public class RSS {

    @SerializedName("title")
    private String title;

    @SerializedName("link")
    private String link;

    @SerializedName("description")
    private String description;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }
}
