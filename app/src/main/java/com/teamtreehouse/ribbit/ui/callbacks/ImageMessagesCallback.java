package com.teamtreehouse.ribbit.ui.callbacks;

import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.ImageMessage;

/**
 * Created by javie on 4/13/2017.
 */

public class ImageMessagesCallback implements ChildEventListener{

    private ImageMessageListener listener;

    public interface ImageMessageListener {

        void onMessageAdded(ImageMessage message);
    }
    public ImageMessagesCallback(ImageMessageListener listener) {

        this.listener = listener;
        FirebaseDatabase
            .getInstance()
            .getReference()
            .child("messages")
            .child("images")
            .child(Auth.getInstance().getId())
            .addChildEventListener(this);
    }
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onMessageAdded(dataSnapshot.getValue(ImageMessage.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
