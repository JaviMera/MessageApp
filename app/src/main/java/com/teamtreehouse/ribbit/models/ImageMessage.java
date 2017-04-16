package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

import com.google.firebase.storage.UploadTask;

/**
 * Created by javie on 4/13/2017.
 */

public class ImageMessage extends Message{

    private String url;
    private String path;

    public ImageMessage(){
        super("", "", 0);
    }

    public ImageMessage(String id, String username, String url, String path, long date) {

        super(id, username, date);
        this.url = url;
        this.path = path;
    }

    protected ImageMessage(Parcel in) {
        super(in);

        url = in.readString();
        path = in.readString();
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
