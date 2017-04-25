package com.example.artman2111.creditcards.controller.wrapersdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.artman2111.creditcards.db.DB;

/**
 * Created by artman2111 on 09.02.17.
 */

public abstract class BaseDBWraper {
    private DB m_DBHelper;
    private String m_strTableName;

    public BaseDBWraper(Context context, String strTableName){
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
