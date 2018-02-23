package com.example.caporal.tecnutriapp.domain.entity;

/**
 * Created by caporal on 22/02/18.
 */

public class Profile {

    private long id;
    private String name;
    private String image;
    private String general_goal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGeneral_goal() {
        return general_goal;
    }

    public void setGeneral_goal(String general_goal) {
        this.general_goal = general_goal;
    }
}
