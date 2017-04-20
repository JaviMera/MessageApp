package com.teamtreehouse.ribbit.ui.activities.intents;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.teamtreehouse.ribbit.ui.activities.messages.ImageMessageActivity;
import com.teamtreehouse.ribbit.utils.FileHelper;

/**
 * Created by javie on 4/18/2017.
 */

public class PictureCaptureResultIntent implements MultimediaResultIntent<ImageMessageActivity> {

    @Override
    public Class<ImageMessageActivity> getActivity() {

        return ImageMessageActivity.class;
    }

    @Override
    public Uri getUri(Intent intent, Context context) {

        String imagePath = FileHelper.getPath((Bitmap) intent.getExtras().get("data"), context);
        return Uri.parse(imagePath);
    }
}
