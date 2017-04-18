package com.teamtreehouse.ribbit.ui.fragments.inbox.view;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.DeleteVideoCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.MessageStorage;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.MessageDuration;
import com.teamtreehouse.ribbit.models.VideoDuration;
import com.teamtreehouse.ribbit.models.VideoMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/17/2017.
 */

public class ViewVideoMessageFragment extends ViewFragmentMessage implements ViewMessageFragmentView {

    private VideoDuration duration;

    @BindView(R.id.videoView)
    VideoView videoView;

    private VideoMessage message;

    public static ViewVideoMessageFragment newInstance(VideoMessage message) {

        ViewVideoMessageFragment fragment = new ViewVideoMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Message.KEY, message);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_video_message, container, false);

        ButterKnife.bind(this, view);
        message = getArguments().getParcelable(Message.KEY);

        parent.setProgressbarVisibility(View.VISIBLE);
        parent.setCardViewVisibility(View.GONE);

        final File file;
        try {
            file = File.createTempFile ("temp", "mp4");
            FirebaseStorage
                .getInstance()
                .getReference()
                .child(message.getPath())
                .getFile(file)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    @SuppressWarnings("VisibleForTests")
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Uri uri = Uri.fromFile(file);
                        videoView.setVideoURI(uri);
                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {

                                duration = new VideoDuration(mediaPlayer.getDuration());
                                parent.setProgressbarVisibility(View.GONE);
                                parent.setCardViewVisibility(View.VISIBLE);
                                parent.start();
                                videoView.start();
                            }
                        });
                    }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public VideoDuration getMessageDuration() {

        return this.duration;
    }

    @Override
    public void onFinish() {

        // Delete the video from firebase storage
        MessageStorage.deleteVideo(message.getPath(), new DeleteVideoCallback() {
            @Override
            public void onSuccess() {

                MessageDB.deleteVideoMessage(message, new DeleteVideoCallback() {
                    @Override
                    public void onSuccess() {

                        Toast.makeText(parent, "Message Viewed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message) {

                        Toast.makeText(parent, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
