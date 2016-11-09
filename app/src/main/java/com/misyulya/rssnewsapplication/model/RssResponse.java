package com.misyulya.rssnewsapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 1 on 30.06.2016.
 */
public class RssResponse {

    @SerializedName("filmsData")
    private List<RssItem> rssItems;

    public List<RssItem> getRssData() {
        for (RssItem item : rssItems) {
            String posterUrl = item.getPosterURL();
            if (posterUrl != null) {
                String start = posterUrl.substring(0, 18);
                String end = "360" + posterUrl.substring(18);
                String trueUrl = "https://st.kinopoisk.ru/images/" + start + end;
                item.setPosterURL(trueUrl);
            }
        }
        return rssItems;
    }
}
