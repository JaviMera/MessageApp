package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserInvite;

/**
 * Created by javie on 3/31/2017.
 */

public class InvitesCallback implements ChildEventListener {

    private final OnInviteListener listener;

    public InvitesCallback(OnInviteListener listener) {

        this.listener = listener;

        MessageDB.readSenderInvites(Auth.getInstance().getId(), this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onInvitesAdded(dataSnapshot.getValue(UserInvite.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        this.listener.onInviteRemoved(dataSnapshot.getValue(UserInvite.class));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public interface OnInviteListener {

        void onInvitesAdded(User userInvite);
        void onInviteRemoved(User userInvite);
    }
}