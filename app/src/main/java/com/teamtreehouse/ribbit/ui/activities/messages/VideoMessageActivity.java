package com.teamtreehouse.ribbit.ui.activities.messages;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.ImageStorageCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.MultimediaInsertCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.ImageMessage;
import com.teamtreehouse.ribbit.models.messages.MultimediaMessage;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentVideoMessage;

import java.util.Date;
import java.util.UUID;

import butterknife.OnClick;

/**
 * Created by javie on 4/17/2017.
 */

public class VideoMessageActivity extends MessageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.presenter.setSendLayoutVisibility(View.VISIBLE);
        this.presenter.setProgressbarVisibility(View.GONE);

        Uri videoUri = getIntent().getParcelableExtra(MultimediaMessage.KEY);
        replaceFragment(R.id.messageContainer, FragmentVideoMessage.newInstance(videoUri));
    }

    @OnClick(R.id.sendTextImage)
    public void onSendClick(View view) {

        this.presenter.setSendLayoutVisibility(View.GONE);
        this.presenter.setProgressbarVisibility(View.VISIBLE);

        FragmentVideoMessage fragment = findFragmentById(R.id.messageContainer);
        Uri videoUri = fragment.getValue();

        for(final User user : recipients) {

            FirebaseStorage
                .getInstance()
                .getReference()
                .child("videos")
                .child(user.getId())
                .child(videoUri.getLastPathSegment())
                .putFile(videoUri)
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
                        ImageMessage message = new ImageMessage(
                            UUID.randomUUID().toString(),
                            Auth.getInstance().getUsername(),
                            url,
                            path,
                            new Date().getTime()
                        );

                        MessageDB.insertVideoMessage(user.getId(), message, new MultimediaInsertCallback() {
                            @Override
                            public void onSuccess() {

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        }

        finish();
    }
}
