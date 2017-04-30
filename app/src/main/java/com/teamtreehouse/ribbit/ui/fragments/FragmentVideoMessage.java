package com.teamtreehouse.ribbit.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.MultimediaMessage;
import com.teamtreehouse.ribbit.ui.fragments.inbox.messages.FragmentMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/17/2017.
 */

public class FragmentVideoMessage extends FragmentMessage<Uri> {

    public static Fragment newInstance(Uri videoUri) {

        FragmentVideoMessage fragment = new FragmentVideoMessage();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MultimediaMessage.KEY, videoUri);
        fragment.setArguments(bundle);

        return fragment;
    }

    @BindView(R.id.videoView)
    VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_message, container, false);

        ButterKnife.bind(this, view);

        this.value = getArguments().getParcelable(MultimediaMessage.KEY);

        this.videoView.setVideoURI(this.value);

        // Set the preview image of the video
        this.videoView.seekTo(100);
        return view;
    }
}
