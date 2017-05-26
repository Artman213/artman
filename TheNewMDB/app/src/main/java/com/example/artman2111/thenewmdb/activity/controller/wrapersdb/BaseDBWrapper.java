package com.example.artman2111.thenewmdb.activity.controller.wrapersdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.artman2111.thenewmdb.activity.db.DB;

/**
 * Created by artman2111 on 16.05.17.
 */

public abstract class BaseDBWrapper {
    private DB m_DBHelper;
    private String m_strTableName;

    public BaseDBWrapper(Context context, String strTableName){
        m_DBHelper = new DB(context);
        m_strTableName = strTableName;
    }

    protected String getTableName(){
        return m_strTableName;
    }

    protected SQLiteDatabase getReadableDB(){
        return m_DBHelper.getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDB(){
        return m_DBHelper.getWritableDatabase();
    }
}
