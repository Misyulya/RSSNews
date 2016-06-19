package com.misyulya.rssnewsapplication.business;

import com.misyulya.rssnewsapplication.models.RssItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 1 on 02.06.2016.
 */
public class RssBusiness {


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

}
