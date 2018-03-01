package com.example.caporal.tecnutriapp.domain.entity;

/**
 * Created by caporal on 28/02/18.
 */

public class MiniPost {

    private String feedHash;
    private String image;
    private Profile profile;

    public String getFeedHash() {
        return feedHash;
    }

    public void setFeedHash(String feedHash) {
        this.feedHash = feedHash;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
