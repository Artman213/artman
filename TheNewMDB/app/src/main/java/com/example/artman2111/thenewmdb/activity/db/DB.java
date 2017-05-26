package com.example.artman2111.thenewmdb.activity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by artman2111 on 16.05.17.
 */

public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBConstants.DB_NAME_INFO + " (" + DBConstants.FILM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.TITLE + " TEXT, "
                + DBConstants.POSTER_PATH + " TEXT, "
                + DBConstants.FILM_SERVER_ID + " TEXT, "
                + DBConstants.OVERVIEW + " TEXT, "
                + DBConstants.RELEASE_DATE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
