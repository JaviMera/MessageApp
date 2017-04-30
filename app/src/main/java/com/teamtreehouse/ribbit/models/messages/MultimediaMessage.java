package com.teamtreehouse.ribbit.models.messages;

import android.os.Parcel;

import com.teamtreehouse.ribbit.models.messages.Message;

/**
 * Created by javie on 4/18/2017.
 */

public abstract class MultimediaMessage extends Message {

    public static final String KEY = "uri";

    private String url;
    private String path;
    public MultimediaMessage(){
        super();
    }

    public MultimediaMessage(String id, String username, String displayName, String text, String url, String path, long date) {

        super(id, username, displayName, text, date);
        this.url = url;
        this.path = path;
    }

    protected MultimediaMessage(Parcel in) {
        super(in);

        url = in.readString();
        path = in.readString();
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {

        return path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        super.writeToParcel(parcel,i);

        parcel.writeString(url);
        parcel.writeString(path);
    }
}
