package com.teamtreehouse.ribbit.models;

import android.content.Context;

import com.teamtreehouse.ribbit.R;

/**
 * Created by javie on 4/30/2017.
 */

public class MessagePicture extends CapturePictureInfo {

    public MessagePicture(Context context) {
        super(context);
    }

    @Override
    public int getRequestCode() {

        return this.context.getResources().getInteger(R.integer.message_take_picture_request);
    }
}
