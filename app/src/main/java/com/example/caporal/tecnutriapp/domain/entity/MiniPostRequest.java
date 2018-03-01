package com.example.caporal.tecnutriapp.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by caporal on 28/02/18.
 */

public class MiniPostRequest {

    @SerializedName("t")
    private Long timestamp;
    @SerializedName("p")
    private int page;
    private List<MiniPost> items;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MiniPost> getItems() {
        return items;
    }

    public void setItems(List<MiniPost> items) {
        this.items = items;
    }

}
