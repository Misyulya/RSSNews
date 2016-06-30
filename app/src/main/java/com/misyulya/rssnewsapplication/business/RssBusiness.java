package com.misyulya.rssnewsapplication.business;

import com.misyulya.rssnewsapplication.models.RssItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 1 on 02.06.2016.
 */
public class RssBusiness {
    private final String RSS_API = "http://scripting.com/rss.json";


    public Collection<RssItem> getRss(){
        Collection<RssItem> rssItemCollection = new ArrayList<>();
        rssItemCollection.add(new RssItem("First chanel", "the best TV chanel"));
        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        rssItemCollection.add(new RssItem("First chanel", "the best TV chanel"));
        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        return rssItemCollection;
    }

    public String getRssResponseString() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(RSS_API)
                .build();

        String responseJson = client.newCall(request).execute().body().toString();
        return responseJson;

    }

}
