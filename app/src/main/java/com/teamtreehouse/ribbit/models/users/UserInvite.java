package com.teamtreehouse.ribbit.models.users;

import android.os.Parcel;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class UserInvite extends User {

    private int status;

    public UserInvite(String uId, String username, int status) {
        super(uId, username);

        this.status = status;
    }

    public UserInvite() {
        super();
    }

    public int getStatus() {

        return this.status;
    }

    @Override
    public String getEmail() {

        return "";
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);

        parcel.writeInt(this.status);
    }

    public UserInvite(Parcel in) {
        super(in);

        this.status = in.readInt();
    }
}
