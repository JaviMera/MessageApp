package com.teamtreehouse.ribbit.database;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

/**
 * Created by javie on 4/15/2017.
 */

public class MessageStorage {

    public static final String IMAGES_NODE = "images";

    public static void insertPicture(String userId, Uri uri, final ImageInsertCallback callback) {

        FirebaseStorage
            .getInstance()
            .getReference()
            .child(IMAGES_NODE)
            .child(userId)
            .child(uri.getLastPathSegment())
            .putFile(uri)
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                }
            })
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                @SuppressWarnings("VisibleForTests")
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String url = taskSnapshot.getMetadata().getDownloadUrl().toString();
                    String path = taskSnapshot.getMetadata().getPath();
                    callback.onSuccess(url, path);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    callback.onFailure();
                }
            });
    }

    public static void deletePicture(String path, final DeletePictureCallback callback) {

        FirebaseStorage
            .getInstance()
            .getReference()
            .child(path)
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    callback.onSuccess();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    callback.onFailure(e.getMessage());
                }
            });
    }
}
