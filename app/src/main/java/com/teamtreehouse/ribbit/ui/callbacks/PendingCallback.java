package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserPending;

/**
 * Created by javie on 3/31/2017.
 */

public class PendingCallback implements ChildEventListener {

    private final OnPendingListener listener;

    public interface OnPendingListener {

        void onPendingAdded(User user);
    }

    public PendingCallback(OnPendingListener listener) {

        this.listener = listener;
        MessageDB.readRecipientInvites(Auth.getInstance().getId(), this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onPendingAdded(dataSnapshot.getValue(UserPending.class));
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