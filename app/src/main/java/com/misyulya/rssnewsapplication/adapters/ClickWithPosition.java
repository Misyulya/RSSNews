package com.misyulya.rssnewsapplication.adapters;

import com.misyulya.rssnewsapplication.models.RssItem;

/**
 * Created by 1 on 08.06.2016.
 */
public interface ClickWithPosition {
    void onLongClickWithPosition(RssItem item, int itemPosition);
    void onClickWithPosition(RssItem item, int itemPosition);
}
