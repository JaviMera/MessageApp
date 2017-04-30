package com.teamtreehouse.ribbit.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 4/13/2017.
 */

public class VideoMessage extends MultimediaMessage {

    public VideoMessage(){}

    public VideoMessage(String id, String username, String displayName, String text, String url, String path, long time){
        super(id, username, displayName, text, url, path, time);
    }

    public static final Parcelable.Creator<VideoMessage> CREATOR = new Parcelable.Creator<VideoMessage>() {
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
