package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 4/9/2017.
 */

public class UserSelectable extends User {

    private boolean selected;

    public UserSelectable(){
        super();
    }

    public UserSelectable(String uId, String userName) {
        super(uId, userName);
    }

    public static final Creator<UserSelectable> CREATOR = new Creator<UserSelectable>() {
        @Override
        public UserSelectable createFromParcel(Parcel in) {
            return new UserSelectable(in);
        }

        @Override
        public UserSelectable[] newArray(int size) {
            return new UserSelectable[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);

        parcel.writeByte((byte) (selected ? 1 : 0));
    }

    public UserSelectable(Parcel in) {
        super(in);

        this.selected = in.readByte() == 1;
    }

    public void select(boolean checked) {

        this.selected = checked;
    }
}
