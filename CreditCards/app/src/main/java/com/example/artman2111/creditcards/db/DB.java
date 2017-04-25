package com.example.artman2111.creditcards.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.artman2111.creditcards.appSetingsAndConstants.DBConstants;

/**
 * Created by artman2111 on 06.02.17.
 */

public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBConstants.DB_NAME_INFO + " (" + DBConstants.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.USER_NAME + " TEXT NOT NULL, "
                + DBConstants.USER_SURNAME + " TEXT, "
                + DBConstants.USER_CARD1 + " TEXT, "
                + DBConstants.USER_CARD2 + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
