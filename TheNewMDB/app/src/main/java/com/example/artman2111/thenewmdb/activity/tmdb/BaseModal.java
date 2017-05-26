package com.example.artman2111.thenewmdb.activity.tmdb;

/**
 * Created by artman2111 on 14.03.17.
 */

public class  BaseModal {

    private final static String POP_URL
            = "https://api.themoviedb.org/3/movie/popular?api_key=94edada748dbcb0dbb6dded2fe0d5c82&language=ru&page=";
    private final static String TOP_URL
            = "https://api.themoviedb.org/3/movie/top_rated?api_key=94edada748dbcb0dbb6dded2fe0d5c82&language=ru&page=";
    private final static String API_KEY = "94edada748dbcb0dbb6dded2fe0d5c82";
    private String GALLERY_URL;
    private static final String URL_BASE = "https://api.themoviedb.org/3/search/";

    public String getPopUrl(){
        return POP_URL;
    }
    public String getTopUrl(){
        return TOP_URL;
    }
    public String getGalдeryUrl(String movieID){
        GALLERY_URL = "https://api.themoviedb.org/3/movie/"+movieID+"/images?api_key="
                +API_KEY;
        return GALLERY_URL;

    }
    public  static String getUrlBase(){
        return URL_BASE;
    }

}
