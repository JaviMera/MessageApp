package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.users.UserFriend;

/**
 * Created by javie on 3/31/2017.
 */

public class FriendsCallback implements ChildEventListener {

    private final FriendsListener listener;

    public FriendsCallback(String userId, FriendsListener listener) {

        MessageDB.readFriends(userId, this);
        this.listener = listener;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        this.listener.onFriendAdded(dataSnapshot.getValue(UserFriend.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        this.listener.onFriendRemoved(dataSnapshot.getValue(UserFriend.class));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
