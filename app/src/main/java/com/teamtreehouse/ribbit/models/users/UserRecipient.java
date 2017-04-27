package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserRecipient extends User {

    public UserRecipient(){
        super();
    }

    public UserRecipient(String uId, String email, String username, String photoUrl) {

        super(uId, email, username, photoUrl);
    }

    public UserRecipient(String uId, String username) {

        super(uId, "", username, "");
    }

    public static final Creator<UserRecipient> CREATOR = new Creator<UserRecipient>() {
        @Override
        public UserRecipient createFromParcel(Parcel in) {
            return new UserRecipient(in);
        }

        @Override
        public UserRecipient[] newArray(int size) {
            return new UserRecipient[size];
        }
    };

    public UserRecipient(Parcel in) {
        super(in);
    }
}
