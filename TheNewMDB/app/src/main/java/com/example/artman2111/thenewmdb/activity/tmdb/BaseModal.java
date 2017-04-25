package com.example.artman2111.thenewmdb.activity.tmdb;

/**
 * Created by artman2111 on 14.03.17.
 */

public abstract class  BaseModal {

    private final static String POP_URL = "https://api.themoviedb.org/3/movie/popular?api_key=94edada748dbcb0dbb6dded2fe0d5c82&language=ru&page=";
    private final static String TOP_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=94edada748dbcb0dbb6dded2fe0d5c82&language=ru&page=";
    private final static String API_KEY = "94edada748dbcb0dbb6dded2fe0d5c82";

    public String getPopUrl(){
        return POP_URL;
    }
    public String getTopUrl(){
        return TOP_URL;
    }
    public String getApiKey(){
        return API_KEY;
    }

}
