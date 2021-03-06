package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.UserRecipient;

/**
 * Created by javie on 3/31/2017.
 */

public class InvitesCallback implements ChildEventListener {

    private final RecipientListener listener;

    public InvitesCallback(String userId, RecipientListener listener) {

        this.listener = listener;

        MessageDB.readSenderInvites(userId, this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onInvitesAdded(dataSnapshot.getValue(UserRecipient.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        this.listener.onInviteRemoved(dataSnapshot.getValue(UserRecipient.class));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
