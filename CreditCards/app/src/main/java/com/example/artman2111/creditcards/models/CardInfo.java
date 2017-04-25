package com.example.artman2111.creditcards.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.artman2111.creditcards.appSetingsAndConstants.DBConstants;

/**
 * Created by artman2111 on 09.02.17.
 */

public class CardInfo {
    private long m_nId = -1;
    private String m_strName;
    private String m_strSName;
    private String m_strCard1;
    private String m_strCard2;

    public CardInfo(String m_strName, String m_strSName, String m_strCard1, String m_strCard2) {
        this.m_strName = m_strName;
        this.m_strSName = m_strSName;
        this.m_strCard1 = m_strCard1;
        this.m_strCard2 = m_strCard2;
    }

    public CardInfo(Cursor cursor){
        this.m_nId = cursor.getLong(cursor.getColumnIndex(DBConstants.USER_ID));
        this.m_strName = cursor.getString(cursor.getColumnIndex(DBConstants.USER_NAME));
        this.m_strSName = cursor.getString(cursor.getColumnIndex(DBConstants.USER_SURNAME));
        this.m_strCard1 = cursor.getString(cursor.getColumnIndex(DBConstants.USER_CARD1));
        this.m_strCard2 = cursor.getString(cursor.getColumnIndex(DBConstants.USER_CARD2));
    }

    public long getId() {
        return m_nId;
    }

    public void setId(long m_nId) {
        this.m_nId = m_nId;
    }

    public String getName() {
        return m_strName;
    }

    public void setName(String m_strName) {
        this.m_strName = m_strName;
    }

    public String getSName() {
        return m_strSName;
    }

    public void setSName(String m_strSName) {
        this.m_strSName = m_strSName;
    }

    public String getCard1() {
        return m_strCard1;
    }

    public void setPhone(String m_strPhone) {
        this.m_strCard1 = m_strPhone;
    }

    public String getCard2() {
        return m_strCard2;
    }

    public void setMail(String m_strMail) {
        this.m_strCard2 = m_strMail;
    }

    public boolean validate(){
        return m_strName != null && !m_strName.isEmpty();
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(DBConstants.USER_NAME, m_strName);
        values.put(DBConstants.USER_SURNAME, m_strSName);
        values.put(DBConstants.USER_CARD1, m_strCard1);
        values.put(DBConstants.USER_CARD2, m_strCard2);
        return values;
    }
}
