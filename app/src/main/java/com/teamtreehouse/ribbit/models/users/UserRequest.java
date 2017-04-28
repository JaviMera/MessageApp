package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;

/**
 * Created by javie on 3/29/2017.
 */

public class UserRequest extends User {

    public UserRequest(){
        super();
    }

    public UserRequest(String uId, String email, String userName, String photoUrl) {
        super(uId, email, userName, "", photoUrl);
    }

    public static final Creator<UserRequest> CREATOR = new Creator<UserRequest>() {
        @Override
        public UserRequest createFromParcel(Parcel in) {
            return new UserRequest(in);
        }

        @Override
        public UserRequest[] newArray(int size) {
            return new UserRequest[size];
        }
    };

    public UserRequest(Parcel in) {
        super(in);
    }
}
