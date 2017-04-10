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

    public static final Creator<UserSender> CREATOR = new Creator<UserSender>() {
        @Override
        public UserSender createFromParcel(Parcel in) {
            return new UserSender(in);
        }

        @Override
        public UserSender[] newArray(int size) {
            return new UserSender[size];
        }
    };

    public UserSender(Parcel in) {
        super(in);
    }
}
