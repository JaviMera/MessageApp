package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.VideoMessage;

/**
 * Created by javie on 4/17/2017.
 */

public class VideoMessagesCallback extends MessageCallback<VideoMessage> {

    private MessageListener<VideoMessage> listener;

    public VideoMessagesCallback(String userId, MessageListener<VideoMessage> listener) {

        super(userId, listener);
    }

    @Override
    protected Class<VideoMessage> getClassType() {

        return VideoMessage.class;
    }

    @Override
    protected String getMessageNode() {

        return "videos";
    }
}
