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

public class VideoMessagesCallback implements ChildEventListener {

    public interface VideoMessageListener {

        void onMessageAdded(VideoMessage message);
        void onMessageRemoved(VideoMessage message);
    }

    private VideoMessageListener listener;

    public VideoMessagesCallback(VideoMessageListener listener) {

        this.listener = listener;

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child("messages")
            .child("videos")
            .child(Auth.getInstance().getId())
            .addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onMessageAdded(dataSnapshot.getValue(VideoMessage.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        this.listener.onMessageRemoved(dataSnapshot.getValue(VideoMessage.class));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
