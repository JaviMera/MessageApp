package com.teamtreehouse.ribbit.ui.activities.intents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.teamtreehouse.ribbit.ui.activities.messages.VideoMessageActivity;

/**
 * Created by javie on 4/18/2017.
 */

public class VideoCaptureResultIntent implements MultimediaResultIntent<VideoMessageActivity> {

    @Override
    public Class<VideoMessageActivity> getActivity() {
        return VideoMessageActivity.class;
    }

    @Override
    public Uri getUri(Intent intent, Context context) {
        return intent.getData();
    }
}
