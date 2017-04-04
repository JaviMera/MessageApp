package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserInvite extends User {

    public UserInvite(){

        super();
    }

    public UserInvite(String uId, String username) {
        super(uId, username);
    }

    @Override
    public String getEmail() {

        return "";
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new UserInvite(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public UserInvite(Parcel in) {
        super(in);
    }
}
