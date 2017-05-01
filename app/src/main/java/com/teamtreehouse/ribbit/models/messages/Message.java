package com.teamtreehouse.ribbit.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.teamtreehouse.ribbit.models.Item;

/**
 * Created by javie on 4/13/2017.
 */

public abstract class Message extends Item implements Parcelable {

    public static final String KEY = "message";

    public abstract String type();

    private String id;
    private String username;
    private String displayName;
    private long date;
    private String text;

    protected Message(){}

    protected Message(String id, String username, String displayName, String text, long date) {

        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.text = text;
        this.date = date;
    }

    public Message(Parcel in) {

        this.id = in.readString();
        username = in.readString();
        displayName = in.readString();
        text = in.readString();
        date = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.id);
        parcel.writeString(username);
        parcel.writeString(displayName);
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

    public String getId() {

        return this.id;
    }

    public long getDate() {
        return date;
    }

    public String getUsername() {

        return this.username;
    }

    public String getDisplayName() {

        return this.displayName;
    }

    public String getText() {
        return this.text;
    }
}
