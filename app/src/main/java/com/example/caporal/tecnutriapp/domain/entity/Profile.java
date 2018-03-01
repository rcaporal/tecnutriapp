package com.example.caporal.tecnutriapp.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by caporal on 22/02/18.
 */

public class Profile implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.generalGoal);
    }

    public Profile() {
    }

    protected Profile(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.image = in.readString();
        this.generalGoal = in.readString();
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
