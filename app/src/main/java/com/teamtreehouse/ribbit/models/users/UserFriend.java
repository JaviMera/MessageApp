package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;

/**
 * Created by javie on 3/30/2017.
 */

public class UserFriend extends User {

    public UserFriend(){super();}

    public UserFriend(String uId, String email, String username) {
        super(uId, email, username);
    }

    public static final Creator<UserFriend> CREATOR = new Creator<UserFriend>() {
        @Override
        public UserFriend createFromParcel(Parcel in) {
            return new UserFriend(in);
        }

        @Override
        public UserFriend[] newArray(int size) {
            return new UserFriend[size];
        }
    };

    public UserFriend(Parcel in) {
        super(in);
    }

}
