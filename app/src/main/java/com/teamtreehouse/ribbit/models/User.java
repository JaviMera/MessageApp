package com.teamtreehouse.ribbit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 4/4/2017.
 */

public abstract class User implements Parcelable {

    private String uId;
    private String email;
    private String username;

    public User() {}

    public User(String uId, String email, String username) {

        this.uId = uId;
        this.email = email;
        this.username = username;
    }

    public User(String uId, String username) {

        this.uId = uId;
        this.username = username;
        this.email = "";
    }

    protected User(Parcel in) {
        uId = in.readString();
        email = in.readString();
        username = in.readString();
    }

    public String getId() {
        return uId;
    }

    public String getEmail() {

        return this.email;
    }

    public String getUsername() {

        return this.username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.uId);
        parcel.writeString(this.email);
        parcel.writeString(this.username);
    }

    public void setId(String id) {

        this.uId = id;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        return prime + this.uId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this)
            return true;

        if(!(obj instanceof User))
            return false;

        User other = (User) obj;
        return this.uId.equals(other.getId());
    }
}
