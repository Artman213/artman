package com.example.artman2111.thenewmdb.activity.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.artman2111.thenewmdb.activity.db.DBConstants;

/**
 * Created by artman2111 on 16.05.17.
 */

public class DBFilmInfo extends BaseAccept{

    private long filmId = -1;
    private String title;
    private String overview;
    private String poster;
    private String release;
    private String serverId;

    public DBFilmInfo(Cursor cursor){
        this.filmId = cursor.getLong(cursor.getColumnIndex(DBConstants.FILM_ID));
        this.title = cursor.getString(cursor.getColumnIndex(DBConstants.TITLE));
        this.overview = cursor.getString(cursor.getColumnIndex(DBConstants.OVERVIEW));
        this.poster = cursor.getString(cursor.getColumnIndex(DBConstants.POSTER_PATH));
        this.release = cursor.getString(cursor.getColumnIndex(DBConstants.RELEASE_DATE));
        this.serverId = cursor.getString(cursor.getColumnIndex(DBConstants.FILM_SERVER_ID));
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String getPoster() {
        return poster;
    }

    @Override
    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }


    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(DBConstants.TITLE, title);
        values.put(DBConstants.OVERVIEW, overview);
        values.put(DBConstants.POSTER_PATH, poster);
        values.put(DBConstants.RELEASE_DATE, release);
        values.put(DBConstants.FILM_SERVER_ID, serverId);
        return values;
    }
}
