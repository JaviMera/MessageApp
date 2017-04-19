package com.teamtreehouse.ribbit.models;

import android.os.Parcel;

/**
 * Created by javie on 4/13/2017.
 */

public class VideoMessage extends MultimediaMessage {

    public VideoMessage(){}

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

    public VideoMessage(Parcel in) {

        super(in);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
