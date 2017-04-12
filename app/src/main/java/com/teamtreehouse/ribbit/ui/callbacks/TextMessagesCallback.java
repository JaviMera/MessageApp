package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.Message;

/**
 * Created by javie on 4/9/2017.
 */

public class TextMessagesCallback implements ChildEventListener {

    private final MessageRecipient listener;

    public interface MessageRecipient {

        void onReceived(Message msg);
        void onRemoved(Message message);
    }

    public TextMessagesCallback(MessageRecipient listener) {

        this.listener = listener;

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child("messages")
            .child("text")
            .child(Auth.getInstance().getId())
            .addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onReceived(dataSnapshot.getValue(Message.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        this.listener.onRemoved(dataSnapshot.getValue(Message.class));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
