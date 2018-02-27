package com.example.caporal.tecnutriapp.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by caporal on 22/02/18.
 */

public class Profile {

    private long id;
    private String name;
    private String image;
    @SerializedName("general_goal")
    private String generalGoal;

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

    public String getGeneralGoal() {
        return generalGoal;
    }

    public void setGeneralGoal(String generalGoal) {
        this.generalGoal = generalGoal;
    }
}
