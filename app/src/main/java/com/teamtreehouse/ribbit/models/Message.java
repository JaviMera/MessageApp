package com.teamtreehouse.ribbit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 4/8/2017.
 */

public class Message extends Item implements Parcelable{

    private String id;
    private String username;
    private String text;
    private long date;

    public Message(){}

    public String getId() {

        return this.id;
    }

    public Message(String id, String username, String text, long date) {

        this.id = id;
        this.username = username;
        this.text = text;
        this.date = date;
    }

    protected Message(Parcel in) {
        this.id = in.readString();
        username = in.readString();
        text = in.readString();
        date = in.readLong();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
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

        if(!(obj instanceof Message))
            return false;

        Message other = (Message) obj;
        return this.id.equals(other.getId());
    }

}
