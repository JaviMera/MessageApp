package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserSender extends UserInvite {

    public UserSender(){
        super();
    }

    public UserSender(String uId, String username, int status) {
        super(uId, username, status);

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new UserSender(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public UserSender(Parcel in) {
        super(in);
    }
}
