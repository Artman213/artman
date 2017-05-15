package com.example.artman2111.thenewmdb.activity.tmdb;

import com.example.artman2111.thenewmdb.activity.models.FilmList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by artman2111 on 11.05.17.
 */

public interface ApiService {
    @GET("movie")
    Call<FilmList> getMyJSON(@Query("api_key") String apiKEY,
                             @Query("language") String language,
                             @Query("query") String query,
                             @Query("page") int page,
                             @Query("include_adult") boolean include_adult);

}
