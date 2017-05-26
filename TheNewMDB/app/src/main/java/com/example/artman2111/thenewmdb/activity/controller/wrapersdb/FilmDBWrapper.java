package com.example.artman2111.thenewmdb.activity.controller.wrapersdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.artman2111.thenewmdb.activity.db.DBConstants;
import com.example.artman2111.thenewmdb.activity.models.DBFilmInfo;

import java.util.ArrayList;

/**
 * Created by artman2111 on 16.05.17.
 */

public class FilmDBWrapper extends BaseDBWrapper {

    public FilmDBWrapper(Context context) {
        super(context, DBConstants.DB_NAME);
    }

    public ArrayList<DBFilmInfo> getAll(){
        ArrayList<DBFilmInfo> arrResult = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    DBFilmInfo dbFilmInfo = new DBFilmInfo(cursor);
                    arrResult.add(dbFilmInfo);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return arrResult;
    }
    public void insert(DBFilmInfo item){
        SQLiteDatabase db = getWritableDB();

        ContentValues values = item.getContentValues();
        db.insert(getTableName(), null, values);
        db.close();

    }

    public void removeById(long nId){
        SQLiteDatabase db = getWritableDB();
        String strSelect = DBConstants.FILM_ID + "=?";
        String[] arrArgs = new String[]{Long.toString(nId)};
        db.delete(getTableName(), strSelect, arrArgs);
        db.close();
    }

}
