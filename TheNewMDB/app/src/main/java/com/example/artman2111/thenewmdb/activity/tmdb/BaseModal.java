package com.example.artman2111.thenewmdb.activity.tmdb;

import android.content.Context;

/**
 * Created by artman2111 on 14.03.17.
 */

public abstract class  BaseModal {
    public final static int RESPONSE_CODE_OK = 200;

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=94edada748dbcb0dbb6dded2fe0d5c82&language=ru&page=1";

    private Context m_Context;
    public String getUrl(){
        return BASE_URL;
    }
}
