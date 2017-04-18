package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 4/13/2017.
 */

public class VideoMessage extends Message{

    public static final String KEY = "uri";

    private String url;
    private String path;

    public VideoMessage(){
        super("", "", 0);
    }

    public VideoMessage(String id, String username, String url, String path, long date) {

        super(id, username, date);
        this.url = url;
        this.path = path;
    }

    protected VideoMessage(Parcel in) {
        super(in);

        url = in.readString();
        path = in.readString();
    }

    public static final Creator<VideoMessage> CREATOR = new Creator<VideoMessage>() {
        @Override
        public VideoMessage createFromParcel(Parcel in) {
            return new VideoMessage(in);
        }

        @Override
        public VideoMessage[] newArray(int size) {
            return new VideoMessage[size];
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
