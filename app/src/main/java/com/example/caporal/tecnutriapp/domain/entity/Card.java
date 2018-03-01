package com.example.caporal.tecnutriapp.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by caporal on 22/02/18.
 */

public class Card implements Parcelable {

    private String feedHash;
    private long id;
    private Profile profile;
    private String image;
    private String date;
    private float energy;
    @SerializedName("meal")
    private int mealType;

    public String getFeedHash() {
        return feedHash;
    }

    public void setFeedHash(String feedHash) {
        this.feedHash = feedHash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMealType() {
        return mealType;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.feedHash);
        dest.writeLong(this.id);
        dest.writeParcelable(this.profile, flags);
        dest.writeString(this.image);
        dest.writeString(this.date);
        dest.writeFloat(this.energy);
        dest.writeInt(this.mealType);
    }

    public Card() {
    }

    protected Card(Parcel in) {
        this.feedHash = in.readString();
        this.id = in.readLong();
        this.profile = in.readParcelable(Profile.class.getClassLoader());
        this.image = in.readString();
        this.date = in.readString();
        this.energy = in.readFloat();
        this.mealType = in.readInt();
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
