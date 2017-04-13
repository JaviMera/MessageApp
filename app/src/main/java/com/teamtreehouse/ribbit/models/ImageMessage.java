package com.teamtreehouse.ribbit.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 4/13/2017.
 */

public class ImageMessage extends Message{

    private String url;
    private String path;
    private String name;

    public ImageMessage(){}

    public ImageMessage(String url, String path, String name) {

        this.url = url;
        this.path = path;
        this.name = name;
    }

    protected ImageMessage(Parcel in) {
        url = in.readString();
        path = in.readString();
        name = in.readString();
    }

    public static final Creator<ImageMessage> CREATOR = new Creator<ImageMessage>() {
        @Override
        public ImageMessage createFromParcel(Parcel in) {
            return new ImageMessage(in);
        }

        @Override
        public ImageMessage[] newArray(int size) {
            return new ImageMessage[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
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

        parcel.writeString(url);
        parcel.writeString(path);
        parcel.writeString(name);
    }
}
