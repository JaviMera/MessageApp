package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 4/13/2017.
 */

public class ImageMessage extends MultimediaMessage{

    public ImageMessage(String id, String username, String url, String path, long time){
        super(id, username, url, path, time);
    }

    protected ImageMessage(Parcel in) {
        super(in);
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        super.writeToParcel(parcel,i);
    }
}
