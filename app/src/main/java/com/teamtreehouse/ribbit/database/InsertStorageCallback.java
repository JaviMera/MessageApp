package com.teamtreehouse.ribbit.database;

import com.google.firebase.storage.UploadTask;

/**
 * Created by javie on 4/15/2017.
 */

public interface InsertStorageCallback {

    void onSuccess(String url, String path);
    void onFailure();
}
