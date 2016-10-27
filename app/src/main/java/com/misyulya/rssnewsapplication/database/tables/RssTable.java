package com.misyulya.rssnewsapplication.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.misyulya.rssnewsapplication.model.RssItem;

/**
 * Created by 1 on 31.03.2016.
 */
public class RssTable {
    public static final String TABLE_NAME = "Rss_item_table";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String GENRE = "genre";
    public static final String POSTER_URL = "poster URL";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID + " integer primary " +
            "key autoincrement, " + TITLE + " text, " + GENRE + " text, " + POSTER_URL + "text);";



    public static void createDataBase(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }


    public static void deleteTable(SQLiteDatabase db) {
        db.execSQL(DELETE_TABLE);
    }

    public static ContentValues createContentValues(RssItem rss) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, rss.getTitle());
        cv.put(GENRE, rss.getGenre());
        cv.put(POSTER_URL, rss.getPosterURL());
        return cv;
    }

}
