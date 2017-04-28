package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.teamtreehouse.ribbit.models.Item;

/**
 * Created by javie on 4/4/2017.
 */

public abstract class User extends Item implements Parcelable, Comparable<User> {

    public static final String KEY = "users";

    private String id;
    private String email;
    private String username;
    private String displayName;
    private String photoUrl;

    public User() {}

    public User(User copyUser) {

        this(
            copyUser.getId(),
            copyUser.getEmail(),
            copyUser.getUsername(),
            copyUser.getDisplayName(),
            copyUser.getPhotoUrl()
        );
    }

    public User(String uId, String email, String username, String displayName, String photoUrl) {

        this.id = uId;
        this.email = email;
        this.username = username;
        this.photoUrl = photoUrl;
        this.displayName = displayName;
    }

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        username = in.readString();
        photoUrl = in.readString();
        displayName = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {

        return this.email;
    }

    public String getUsername() {

        return this.username;
    }

    public String getPhotoUrl() {

        return this.photoUrl;
    }

    public String getDisplayName() {

        return this.displayName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.id);
        parcel.writeString(this.email);
        parcel.writeString(this.username);
        parcel.writeString(this.photoUrl);
        parcel.writeString(this.displayName);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        return prime + this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this)
            return true;

        if(!(obj instanceof User))
            return false;

        User other = (User) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int compareTo(@NonNull User user) {
        return this.getUsername().compareTo(user.getUsername());
    }
}
