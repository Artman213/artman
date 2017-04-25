package com.example.artman2111.thenewmdb.activity.models;

/**
 * Created by artman2111 on 19.04.17.
 */

public class Film_Accept extends Base_Accept {
    private String id;
    private String title;
    private String overview;
    private String release;


    public Film_Accept(String poster ,String id, String title, String overview, String release) {
        setPoster(poster);
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release = release;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}
