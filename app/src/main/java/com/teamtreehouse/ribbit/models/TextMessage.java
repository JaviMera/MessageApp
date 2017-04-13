package com.teamtreehouse.ribbit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 4/8/2017.
 */

public class TextMessage extends Message {

    private String id;
    private String username;
    private String text;
    private long date;

    public TextMessage(){}

    public String getId() {

        return this.id;
    }

    public TextMessage(String id, String username, String text, long date) {

        this.id = id;
        this.username = username;
        this.text = text;
        this.date = date;
    }

    protected TextMessage(Parcel in) {
        this.id = in.readString();
        username = in.readString();
        text = in.readString();
        date = in.readLong();
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

    public long getDate() {
        return date;
    }

    public String getUsername() {

        return this.username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.id);
        parcel.writeString(username);
        parcel.writeString(text);
        parcel.writeLong(date);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        return prime + this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this)
            return true;

        if(!(obj instanceof TextMessage))
            return false;

        TextMessage other = (TextMessage) obj;
        return this.id.equals(other.getId());
    }

}
