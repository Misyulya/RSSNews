package com.misyulya.rssnewsapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.misyulya.rssnewsapplication.database.tables.RssTable;
import com.misyulya.rssnewsapplication.exeption.DbException;
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

    public DataProvider() {
        helper = DBHelper.getInstance();
        db = helper.getWritableDatabase();
    }

    public void setRss(Collection<RssItem> rssItemList) throws DbException {
        db.delete(RssTable.TABLE_NAME, null, null);
        List<Long> result = new ArrayList<>();
        for (RssItem item :
                rssItemList) {
            ContentValues cv;
            cv = RssTable.createContentValues(item);
           result.add(db.insert(RssTable.TABLE_NAME, null, cv));
        }
      if (result.contains(-1))throw new DbException("Ошибка записи в БД");
    }

    public int removeRss(RssItem rssItem) {
        int delCount = db.delete(RssTable.TABLE_NAME, RssTable.ID + " = " + rssItem.getId(), null);
        return delCount;
    }

    public List<RssItem> getRss() {
        List<RssItem> rssList = new ArrayList<>();
        Cursor c = db.query(RssTable.TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(RssTable.ID);
            int titleColIndex = c.getColumnIndex(RssTable.TITLE);
            int genreColIndex = c.getColumnIndex(RssTable.GENRE);
            int posterUrlColIndex = c.getColumnIndex(RssTable.POSTER_URL);
            do {
                RssItem rssItem = new RssItem();
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
}
