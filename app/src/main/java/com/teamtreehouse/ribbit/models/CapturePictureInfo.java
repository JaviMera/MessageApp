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

public abstract class CapturePictureInfo implements CameraActivityInfo {

    protected final Context context;

    public CapturePictureInfo(Context context) {

        this.context = context;
    }

    @Override
    public File getDir() {

        return this.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public String getAction() {

        return MediaStore.ACTION_IMAGE_CAPTURE;
    }

    @Override
    public String getTimestamp() {

        return this.context.getString(R.string.timestamp);
    }

    @Override
    public String getFileName() {

        return this.context.getString(R.string.picture_file_name);
    }

    @Override
    public String getFileFormat() {

        return this.context.getString(R.string.picture_file_format);
    }

    @Override
    public Intent createIntent() {

        return new Intent();
    }
}
