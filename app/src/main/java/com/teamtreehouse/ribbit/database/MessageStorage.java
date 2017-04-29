package com.teamtreehouse.ribbit.database;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

/**
 * Created by javie on 4/15/2017.
 */

public class MessageStorage {

    public static final String IMAGES_NODE = "images";
    private static final String VIDEOS_NODE = "videos";
    public static final String PROFILE_PICTURES_NODE = "profile_pictures";

    public static void updateProfilePicture(String userId, Uri uri, final MultimediaStorageCallback callback) {

        FirebaseStorage
            .getInstance()
            .getReference()
            .child(PROFILE_PICTURES_NODE)
            .child(userId)
            .putFile(uri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                @SuppressWarnings("VisibleForTests")
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    try {

                        String url = taskSnapshot.getMetadata().getDownloadUrl().toString();
                        callback.onSuccess(url, "");
                    }
                    catch(NullPointerException e) {

                        callback.onFailure(e.getMessage());
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    callback.onFailure(e.getMessage());
                }
            });
    }

    public static void insertPicture(String userId, Uri uri, final MultimediaStorageCallback callback) {

        insertMultimediaFile(IMAGES_NODE, userId, uri, callback);
    }

    public static void insertVideo(String userId, Uri uri, final MultimediaStorageCallback callback) {

        insertMultimediaFile(VIDEOS_NODE, userId, uri, callback);
    }

    public static void deleteMiltumediaFile(String path, final DeleteMultimediaFileCallback callback) {

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

    private static void insertMultimediaFile(String node, String userId, Uri uri, final MultimediaStorageCallback callback) {

        FirebaseStorage
            .getInstance()
            .getReference()
            .child(node)
            .child(userId)
            .child(uri.getLastPathSegment())
            .putFile(uri)
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

                    callback.onFailure(e.getMessage());
                }
            });
    }
}
