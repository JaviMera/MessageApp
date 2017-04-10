package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;

/**
 * Created by javie on 4/9/2017.
 */

public abstract class FriendsCallback implements ChildEventListener {

    public abstract void add(DataSnapshot snapshot);
    public abstract void delete(DataSnapshot snapshot);

    public FriendsCallback() {

        MessageDB.readFriends(Auth.getInstance().getId(), this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        add(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        delete(dataSnapshot);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
