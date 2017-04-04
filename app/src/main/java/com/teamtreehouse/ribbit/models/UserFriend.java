package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 3/30/2017.
 */

public class UserFriend extends User {

    public UserFriend(){super();}

    public UserFriend(String uId, String username) {
        super(uId, username);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new UserFriend(in);
        }

        @Override
        public User[] newArray(int size) {
            return new UserFriend[size];
        }
    };

    public UserFriend(Parcel in) {
        super(in);
    }

}
