package com.example.artman2111.thenewmdb.activity.models;

import android.content.Context;
import android.util.Log;

/**
 * Created by artman2111 on 15.03.17.
 */

public class FilmAcept extends BaseFilm {
    private String poster;
    private String titel;
    private String overview;
    private Context context;
    private String[] JsonParse;


    public FilmAcept (Context context){
        this.context = context;

        Log.d("artman","look --- >>>" + JsonParse);
        for (int i = 0 ; i < JsonParse.length;i++){
            poster = JsonParse[i];
        }
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }




}
