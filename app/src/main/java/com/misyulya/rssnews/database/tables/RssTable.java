package com.misyulya.rssnews.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.misyulya.rssnews.model.RssItem;

/**
 * Created by 1 on 31.03.2016.
 */
public class RssTable {
    public static final String TABLE_NAME = "RssItemTable";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String GENRE = "genre";
    public static final String POSTER_URL = "poster";
    private static final String TEXT_TYPE = " text";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID + " integer primary " +
            "key autoincrement, " + TITLE + TEXT_TYPE + ", " + GENRE + TEXT_TYPE + ", " + POSTER_URL + TEXT_TYPE + ");";

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
