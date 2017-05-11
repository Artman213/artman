package com.example.artman2111.thenewmdb.activity.tmdb;

import com.example.artman2111.thenewmdb.activity.models.FilmAccept;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by artman2111 on 11.05.17.
 */

public interface ApiService {
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("/movie?api_key=94edada748dbcb0dbb6dded2fe0d5c82&language=ru&query="+adress+"&page=1&include_adult=false")
    Call<FilmAccept> getMyJSON();
    String adress = "";
}
