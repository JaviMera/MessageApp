package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;

/**
 * Created by javie on 4/4/2017.
 */

public class UserCurrent extends User {

    public UserCurrent(){super();}

    public UserCurrent(String uId, String email, String username, String displayName, String photoUrl) {
        super(uId, email, username, displayName, photoUrl);
    }

    public static final Creator<UserCurrent> CREATOR = new Creator<UserCurrent>() {
        @Override
        public UserCurrent createFromParcel(Parcel in) {
            return new UserCurrent(in);
        }

        @Override
        public UserCurrent[] newArray(int size) {
            return new UserCurrent[size];
        }
    };

    public UserCurrent(Parcel in) {
        super(in);
    }
}
