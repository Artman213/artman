package com.example.artman2111.thenewmdb.activity.controller.engines;

import android.content.Context;

import com.example.artman2111.thenewmdb.activity.controller.wrapersdb.FilmDBWrapper;
import com.example.artman2111.thenewmdb.activity.models.DBFilmInfo;

import java.util.ArrayList;

/**
 * Created by artman2111 on 16.05.17.
 */

public class FilmEngine extends BaseEngine {

    public FilmEngine(Context context) {
        super(context);
    }

    public ArrayList<DBFilmInfo> getAll(){
        FilmDBWrapper dbWrapper = new FilmDBWrapper(getContext());
        return dbWrapper.getAll();
    }

    public void insert(DBFilmInfo item){
        FilmDBWrapper dbWrapper = new FilmDBWrapper(getContext());
        dbWrapper.insert(item);
    }

    public void removeById(long nId){
        FilmDBWrapper dbWrapper = new FilmDBWrapper(getContext());
        dbWrapper.removeById(nId);
    }
}

