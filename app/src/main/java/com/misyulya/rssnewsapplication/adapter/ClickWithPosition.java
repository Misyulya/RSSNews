package com.misyulya.rssnewsapplication.adapter;

import com.misyulya.rssnewsapplication.model.RssItem;

/**
 * Created by 1 on 08.06.2016.
 */
public interface ClickWithPosition {
    void onLongClickWithPosition(RssItem item, int itemPosition);
    void onClickWithPosition(RssItem item, int itemPosition);
    void finishActionModeAfterDeleteOperation();
}
