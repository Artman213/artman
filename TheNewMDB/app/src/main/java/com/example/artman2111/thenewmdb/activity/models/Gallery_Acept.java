package com.example.artman2111.thenewmdb.activity.models;

/**
 * Created by artman2111 on 15.03.17.
 */

public class Gallery_Acept extends Base_Gallery {
    private String image;

    public Gallery_Acept( String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
