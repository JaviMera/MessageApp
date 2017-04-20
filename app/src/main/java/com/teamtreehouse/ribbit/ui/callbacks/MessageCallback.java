package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.MessageDuration;
import com.teamtreehouse.ribbit.models.TextMessage;

/**
 * Created by javie on 4/19/2017.
 */

public abstract class MessageCallback<TMessage extends Message> implements ChildEventListener{

    private MessageListener<TMessage> listener;
    protected abstract Class<TMessage> getClassType();
    protected abstract String getMessageNode();

    public MessageCallback(String userId, MessageListener<TMessage> listener) {

        this.listener = listener;

        MessageDB.readMessages(userId, getMessageNode(), this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onMessageAdded(dataSnapshot.getValue(getClassType()));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        this.listener.onMessageRemoved(dataSnapshot.getValue(getClassType()));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
