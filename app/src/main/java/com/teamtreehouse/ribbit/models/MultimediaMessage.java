package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 4/18/2017.
 */

public abstract class MultimediaMessage extends Message {

    public static final String KEY = "uri";

    private String url;
    private String path;
    public MultimediaMessage(){
        super("", "", 0);
    }

    public MultimediaMessage(String id, String username, String url, String path, long date) {

        super(id, username, date);
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
