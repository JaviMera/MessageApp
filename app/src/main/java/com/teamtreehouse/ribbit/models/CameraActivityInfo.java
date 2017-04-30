package com.teamtreehouse.ribbit.models;

import android.content.Intent;

import java.io.File;
import java.util.Map;

/**
 * Created by javie on 4/30/2017.
 */

public interface CameraActivityInfo {

    File getDir();
    String getAction();
    String getTimestamp();
    String getFileName();
    String getFileFormat();
    int getRequestCode();
    Intent createIntent();
}
