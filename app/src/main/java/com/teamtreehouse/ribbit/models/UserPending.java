package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserPending extends User {

    public UserPending(){super();}

    public UserPending(String uId, String username) {
        super(uId, username);
    }

    @Override
    public String getEmail() {

        return "";
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new UserPending(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public UserPending(Parcel in) {
        super(in);
    }
}
