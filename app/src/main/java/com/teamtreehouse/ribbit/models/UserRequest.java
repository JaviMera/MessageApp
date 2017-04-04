package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserRequest extends User {

    public UserRequest(){
        super();
    }

    public UserRequest(String uId, String email, String userName) {
        super(uId, email, userName);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new UserRequest(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public UserRequest(Parcel in) {
        super(in);
    }
}
