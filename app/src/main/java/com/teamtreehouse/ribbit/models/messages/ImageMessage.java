package com.teamtreehouse.ribbit.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 4/13/2017.
 */

public class ImageMessage extends MultimediaMessage {

    public ImageMessage() {};

    public ImageMessage(String id, String username, String displayName, String text, String url, String path, long time){
        super(id, username, displayName, text, url, path, time);
    }

    protected ImageMessage(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<ImageMessage> CREATOR = new Parcelable.Creator<ImageMessage>() {
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
