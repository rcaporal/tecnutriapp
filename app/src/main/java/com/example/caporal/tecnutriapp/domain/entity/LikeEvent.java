package com.example.caporal.tecnutriapp.domain.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by caporal on 03/03/18.
 */

public class LikeEvent extends RealmObject {

    @PrimaryKey
    private String feedHash;
    private boolean isLiked;

    public LikeEvent(String feedHash, boolean isLiked) {
        this.feedHash = feedHash;
        this.isLiked = isLiked;
    }

    public LikeEvent() {
    }

    public String getFeedHash() {
        return feedHash;
    }

    public boolean isLiked() {
        return isLiked;
    }
}
