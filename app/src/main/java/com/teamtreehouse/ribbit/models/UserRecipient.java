package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserRecipient extends UserInvite {

    public UserRecipient(){
        super();
    }

    public UserRecipient(String uId, String username, int status) {
        super(uId, username, status);

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new UserRecipient(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public UserRecipient(Parcel in) {
        super(in);
    }
}
