package com.teamtreehouse.ribbit.models.messages;

import android.os.Parcel;

/**
 * Created by javie on 4/8/2017.
 */

public class TextMessage extends Message {

    public TextMessage(){

        super();
    }

    public TextMessage(String id, String username, String displayName, String text, long date) {

        super(id, username, displayName, text, date);
    }

    protected TextMessage(Parcel in) {
        super(in);
    }

    public static final Creator<TextMessage> CREATOR = new Creator<TextMessage>() {
        @Override
        public TextMessage createFromParcel(Parcel in) {
            return new TextMessage(in);
        }

        @Override
        public TextMessage[] newArray(int size) {
            return new TextMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
