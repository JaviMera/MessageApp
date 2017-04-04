package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 4/4/2017.
 */

public class UserCurrent extends User {

    public UserCurrent(){super();}

    public UserCurrent(String uId, String email, String username) {
        super(uId, email, username);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new UserCurrent(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public UserCurrent(Parcel in) {
        super(in);
    }
}
