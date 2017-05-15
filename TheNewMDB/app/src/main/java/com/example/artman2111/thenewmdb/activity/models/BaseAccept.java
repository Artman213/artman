package com.example.artman2111.thenewmdb.activity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by artman2111 on 15.03.17.
 */

public abstract class BaseAccept {
    @SerializedName("poster_path")
    @Expose
    private String poster = null;

    public String getPoster(){
        return poster;
    }
    public void setPoster(String poster){
        this.poster = poster;
    }

}
