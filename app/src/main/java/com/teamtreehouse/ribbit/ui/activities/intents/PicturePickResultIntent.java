package com.teamtreehouse.ribbit.ui.activities.intents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.teamtreehouse.ribbit.ui.activities.messages.ImageMessageActivity;

/**
 * Created by javie on 4/18/2017.
 */

public class PicturePickResultIntent implements MultimediaResultIntent<ImageMessageActivity> {

    @Override
    public Class<ImageMessageActivity> getActivity() {

        return ImageMessageActivity.class;
    }

    @Override
    public Uri getUri(Intent intent, Context context) {

        return intent.getData();
    }
}
