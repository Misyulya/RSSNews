package com.misyulya.rssnewsapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.misyulya.rssnewsapplication.database.tables.RssTable;
import com.misyulya.rssnewsapplication.model.RssItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 1 on 25.04.2016.
 */
public class DataProvider {

    private DBHelper helper;
    private SQLiteDatabase db;
    private Context mContext;

    public DataProvider(Context context) {
        mContext = context;
        helper = new DBHelper(mContext);
        db = helper.getWritableDatabase();
    }

    public void setRss(Collection<RssItem> rssItemList) {
        db.delete(RssTable.TABLE_NAME, null, null);
        for (RssItem rss :
                rssItemList) {
            ContentValues cv;
            cv = RssTable.createContentValues(rss);
            db.insert(RssTable.TABLE_NAME, null, cv);
        }
    }

    public int removeRss(RssItem rssItem) {
        int delCount = db.delete(RssTable.TABLE_NAME, RssTable.ID + " = " + rssItem.getId(), null);
        return delCount;
    }

    public List<RssItem> getRss() {
        List<RssItem> rssList = new ArrayList<>();
        RssItem rssItem = new RssItem();
        Cursor c = db.query(RssTable.TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(RssTable.ID);
            int titleColIndex = c.getColumnIndex(RssTable.TITLE);
            int genreColIndex = c.getColumnIndex(RssTable.GENRE);
            int posterUrlColIndex = c.getColumnIndex(RssTable.POSTER_URL);
            do {
                rssItem.setId(c.getInt(idColIndex));
                rssItem.setTitle(c.getString(titleColIndex));
                rssItem.setGenre(c.getString(genreColIndex));
                rssItem.setPosterURL(c.getString(posterUrlColIndex));
                rssList.add(rssItem);
            } while (c.moveToNext());
        }
        c.close();
        return rssList;
    }

//    public boolean saveDataToDB(Rss rss) {
//
//        boolean result = true;
//        if (db.insert(RssTable.TABLE_NAME, null, RssTable.createContentValues(rss)) == -1) {
//            result = false;
//        }
//        return result;
//    }

}
