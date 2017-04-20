package com.teamtreehouse.ribbit.models.messages;

import android.os.Parcel;

/**
 * Created by javie on 4/8/2017.
 */

public class TextMessage extends Message {

    private String text;

    public TextMessage(){
        super("", "",0);
    }

    public TextMessage(String id, String username, String text, long date) {

        super(id, username, date);
        this.text = text;
    }

    protected TextMessage(Parcel in) {
        super(in);

        this.text = in.readString();
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

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        super.writeToParcel(parcel,i);

        parcel.writeString(text);
    }
}
