package com.misyulya.rssnewsapplication.business;

import android.content.Context;

import com.misyulya.rssnewsapplication.database.DataProvider;
import com.misyulya.rssnewsapplication.model.RssItem;

import java.util.List;

/**
 * Created by 1 on 02.06.2016.
 */
public class RssBusiness {
    private DataProvider mDataProvider;

    public RssBusiness(Context context) {
        this.mDataProvider = new DataProvider(context);
    }

    public List<RssItem> getRss() {

        List<RssItem> rssItemsList = mDataProvider.getRss();
//        rssItemCollection = mDataProvider.getRss();
//        rssItemCollection.add(new RssItem("First chanel", "the best TV chanel"));
//        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
//        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
//        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
//        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
//        rssItemCollection.add(new RssItem("First chanel", "the best TV chanel"));
//        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
//        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
//        rssItemCollection.add(new RssItem("Second chanel", "Russian TV chanel"));
//        rssItemCollection.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        return rssItemsList;
    }

    public void saveRssToDB(List<RssItem> rssItems){
        mDataProvider.setRss(rssItems);
    }

    public void delete(RssItem item) {
        mDataProvider.removeRss(item);
    }

//    public String getRssResponseString() throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(RSS_API)
//                .build();
//
//        String responseJson = client.newCall(request).execute().body().toString();
//        return responseJson;
//
//    }


}
