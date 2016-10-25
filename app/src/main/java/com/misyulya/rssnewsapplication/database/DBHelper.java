package com.misyulya.rssnewsapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.misyulya.rssnewsapplication.database.tables.RssTable;

/**
 * Created by 1 on 24.04.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Rss_dataBase";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        RssTable.createDataBase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        RssTable.deleteTable(db);
        onCreate(db);
    }
}
