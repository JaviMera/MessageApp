package com.teamtreehouse.ribbit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 4/4/2017.
 */

public abstract class User implements Parcelable {

    private String uId;
    private String email;
    private String userName;

    public User() {}

    public User(String uId, String email, String userName) {

        this.uId = uId;
        this.email = email;
        this.userName = userName;
    }

    protected User(Parcel in) {
        uId = in.readString();
        email = in.readString();
        userName = in.readString();
    }

    public String getId() {
        return uId;
    }

    public String getEmail() {

        return this.email;
    }

    public String getUserName() {

        return this.userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.uId);
        parcel.writeString(this.email);
        parcel.writeString(this.userName);
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
