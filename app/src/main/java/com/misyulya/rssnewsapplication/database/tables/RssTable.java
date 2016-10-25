package com.misyulya.rssnewsapplication.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.misyulya.rssnewsapplication.models.Rss;
import com.misyulya.rssnewsapplication.models.RssItem;

/**
 * Created by 1 on 31.03.2016.
 */
public class RssTable {
    public static final String TABLE_NAME = "Rss_item_table";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID + " integer primary " +
            "key autoincrement, " + TITLE + " text, " + LINK + " text, " + DESCRIPTION + "text);";



    public static void createDataBase(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }


    public static void deleteTable(SQLiteDatabase db) {
        db.execSQL(DELETE_TABLE);
    }

    public static ContentValues createContentValues(RssItem rss) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, rss.getTitle());
        cv.put(DESCRIPTION, rss.getDescription());
        return cv;
    }

}
