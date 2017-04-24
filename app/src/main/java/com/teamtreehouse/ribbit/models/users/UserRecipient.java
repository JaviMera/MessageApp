package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserRecipient extends UserInvite {

    public UserRecipient(){
        super();
    }

    public UserRecipient(String uId, String email, String username, String photoUrl, int status) {

        super(uId, email, username, photoUrl, status);
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
