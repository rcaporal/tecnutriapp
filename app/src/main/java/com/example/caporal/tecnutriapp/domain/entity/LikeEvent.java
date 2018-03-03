package com.example.caporal.tecnutriapp.domain.entity;

/**
 * Created by caporal on 03/03/18.
 */

public class LikeEvent {

    private String feedHash;
    private boolean isLiked;

    public LikeEvent(String feedHash, boolean isLiked) {
        this.feedHash = feedHash;
        this.isLiked = isLiked;
    }

    public String getFeedHash() {
        return feedHash;
    }

    public boolean isLiked() {
        return isLiked;
    }
}
