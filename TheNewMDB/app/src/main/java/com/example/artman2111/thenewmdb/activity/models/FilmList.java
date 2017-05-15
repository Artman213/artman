package com.example.artman2111.thenewmdb.activity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by artman2111 on 15.05.17.
 */

public class FilmList {
    @SerializedName("results")
    @Expose
    private ArrayList<FilmAccept> filmAccepts = new ArrayList<>();
    public ArrayList<FilmAccept> getFilmAccepts(){
        return filmAccepts;
    }
    public void setFilmAccepts(ArrayList<FilmAccept> filmAccepts){
        this.filmAccepts = filmAccepts;

    }
}
