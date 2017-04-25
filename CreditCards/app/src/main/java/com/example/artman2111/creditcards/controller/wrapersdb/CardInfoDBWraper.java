package com.example.artman2111.creditcards.controller.wrapersdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.artman2111.creditcards.appSetingsAndConstants.DBConstants;
import com.example.artman2111.creditcards.models.CardInfo;

import java.util.ArrayList;

/**
 * Created by artman2111 on 09.02.17.
 */

public class CardInfoDBWraper extends BaseDBWraper {
    public CardInfoDBWraper(Context context) {
        super(context, DBConstants.DB_NAME_INFO);
    }

        public ArrayList<CardInfo> getAll(){
            ArrayList<CardInfo> arrResult = new ArrayList<>();
            SQLiteDatabase db = getReadableDB();
            Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);
            if (cursor!=null) {
                if (cursor.moveToFirst()) {
                    do {
                        CardInfo userInfo = new CardInfo(cursor);
                        arrResult.add(userInfo);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
            return arrResult;
        }

    public CardInfo getUserById(long nId){
        CardInfo userInfo = null;
        SQLiteDatabase db = getReadableDB();

        String strSelect = DBConstants.USER_ID + "=?";
        String[] arrArgs = new String[]{Long.toString(nId)};

        Cursor cursor = db.query(getTableName(),null,
                strSelect, arrArgs,null, null, null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                userInfo = new CardInfo(cursor);
            }
            cursor.close();
        }
        db.close();
        return userInfo;
    }

    public ArrayList<CardInfo> getUserByField(String strSearch){
        String strSearchProcessing = strSearch + "%";
        ArrayList<CardInfo> arrResult = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        String strSelect = DBConstants.USER_NAME + " LIKE ? OR " +
                DBConstants.USER_SURNAME + " LIKE ? OR " +
                DBConstants.USER_CARD1 + " LIKE ? OR " +
                DBConstants.USER_CARD2 + " LIKE ?";
        String[] arrArgs = new String[]{strSearchProcessing , strSearchProcessing, strSearchProcessing, strSearchProcessing};

        Cursor cursor = db.query(getTableName(),null,
                strSelect, arrArgs,null, null, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    CardInfo userInfo = new CardInfo(cursor);
                    arrResult.add(userInfo);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return arrResult;
    }

    public void update(CardInfo item){
        SQLiteDatabase db = getWritableDB();
        String strSelect = DBConstants.USER_ID + "=?";
        String[] arrArgs = new String[]{Long.toString(item.getId())};
        ContentValues values = item.getContentValues();
        db.update(getTableName(), values, strSelect, arrArgs);
        db.close();

    }

    public void insert(CardInfo item){
        SQLiteDatabase db = getWritableDB();

        ContentValues values = item.getContentValues();
        db.insert(getTableName(), null, values);
        db.close();

    }

    public void remove(CardInfo item){
        SQLiteDatabase db = getWritableDB();
        String strSelect = DBConstants.USER_ID + "=?";
        String[] arrArgs = new String[]{Long.toString(item.getId())};
        db.delete(getTableName(), strSelect, arrArgs);
        db.close();
    }

    public void removeById(long nId){
        SQLiteDatabase db = getWritableDB();
        String strSelect = DBConstants.USER_ID + "=?";
        String[] arrArgs = new String[]{Long.toString(nId)};
        db.delete(getTableName(), strSelect, arrArgs);
        db.close();
    }

    public void removeAll(){
        SQLiteDatabase db = getWritableDB();

        db.delete(getTableName(),null,null);

        db.close();
    }
}

