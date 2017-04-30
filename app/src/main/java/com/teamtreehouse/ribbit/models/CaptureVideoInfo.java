package com.teamtreehouse.ribbit.models;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;

import com.teamtreehouse.ribbit.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by javie on 4/30/2017.
 */

public class CaptureVideoInfo implements CameraActivityInfo {

    private final Context context;

    public CaptureVideoInfo(Context context) {

        this.context = context;
    }

    @Override
    public File getDir() {

        return this.context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
    }

    @Override
    public String getAction() {

        return MediaStore.ACTION_VIDEO_CAPTURE;
    }

    @Override
    public String getTimestamp()
    {
        return this.context.getString(R.string.timestamp);
    }

    @Override
    public String getFileName() {

        return this.context.getString(R.string.video_file_name);
    }

    @Override
    public String getFileFormat() {

        return this.context.getString(R.string.video_file_format);
    }

    @Override
    public int getRequestCode() {

        return this.context.getResources().getInteger(R.integer.message_take_video_request);
    }

    @Override
    public Intent createIntent() {

        Intent intent = new Intent();
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);

        return intent;
    }
}
