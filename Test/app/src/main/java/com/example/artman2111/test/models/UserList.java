package com.example.artman2111.test.models;

import android.database.Cursor;

import com.example.artman2111.test.db.DataBaseConstans;

/**
 * Created by artman2111 on 26.05.17.
 */

public class UserList {

    private String username;
    private String last_login;

    public UserList(String username,String last_login) {
        this.username = username;
        this.last_login = last_login;

    }
    public UserList(Cursor cursor) {
        this.username = cursor.getString(cursor.getColumnIndex(DataBaseConstans.DB_V1.TABLE_USER_INFO.USER_INFO_FIELD_USERNAME));
        this.last_login = cursor.getString(cursor.getColumnIndex(DataBaseConstans.DB_V1.TABLE_USER_INFO.USER_INFO_FIELD_LAST_LOGIN));
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

}
