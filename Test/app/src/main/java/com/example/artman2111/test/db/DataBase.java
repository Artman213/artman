package com.example.artman2111.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by artman2111 on 26.05.17.
 */

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context,DataBaseConstans.DB_NAME,null, DataBaseConstans.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DataBaseConstans.DB_V1.TABLE_USER_INFO.TABLE_NAME +
                " (" + DataBaseConstans.DB_V1.TABLE_USER_INFO.USER_INFO_FIELD_NAME_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataBaseConstans.DB_V1.TABLE_USER_INFO.USER_INFO_FIELD_USERNAME + " TEXT NOT NULL, "
                + DataBaseConstans.DB_V1.TABLE_USER_INFO.USER_INFO_FIELD_LAST_LOGIN + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInDB(String username,String lastlogin){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalue = new ContentValues();
        contentvalue.put(DataBaseConstans.DB_V1.TABLE_USER_INFO.USER_INFO_FIELD_USERNAME,username);
        contentvalue.put(DataBaseConstans.DB_V1.TABLE_USER_INFO.USER_INFO_FIELD_LAST_LOGIN,lastlogin);
        long result = db.insert(DataBaseConstans.DB_V1.TABLE_USER_INFO.TABLE_NAME,null,contentvalue);


    }

}
