package com.example.caporal.tecnutriapp.domain.entity;

import java.util.List;

/**
 * Created by caporal on 22/02/18.
 */

public class Feed {

    private long timestamp;
    private int page;
    private List<Card> items;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Card> getItems() {
        return items;
    }

    public void setItems(List<Card> items) {
        this.items = items;
    }

}
