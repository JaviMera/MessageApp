package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserSender extends User {

    public UserSender(){
        super();
    }

    public UserSender(String uId, String email, String username, String photoUrl) {
        super(uId, email, username, photoUrl);
    }

    public UserSender(String uId, String username) {
        super(uId, "", username, "");
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
