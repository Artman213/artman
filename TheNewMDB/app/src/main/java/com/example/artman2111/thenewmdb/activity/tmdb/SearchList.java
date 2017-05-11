package com.example.artman2111.thenewmdb.activity.tmdb;

import com.example.artman2111.thenewmdb.activity.models.FilmAccept;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by artman2111 on 11.05.17.
 */

public class SearchList {
    @SerializedName("Search")
    @Expose
    private ArrayList<FilmAccept> searchFilm = new ArrayList<>();
    public ArrayList<FilmAccept> getSearchFilm(){
        return searchFilm;
    }
    public void setSearchFilm(ArrayList<FilmAccept> searchFilm){
        this.searchFilm = searchFilm;
    }
}
