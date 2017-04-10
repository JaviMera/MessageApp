package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;

/**
 * Created by javie on 3/31/2017.
 */

public class EditableFriendsCallback extends FriendsCallback {

    private final FriendsListener<UserFriend> listener;

    public EditableFriendsCallback(FriendsListener listener) {

        super();

        this.listener = listener;
    }

    @Override
    public void add(DataSnapshot snapshot) {

        this.listener.onFriendAdded(snapshot.getValue(UserFriend.class));
    }

    @Override
    public void delete(DataSnapshot snapshot) {

        this.listener.onFriendRemoved(snapshot.getValue(UserFriend.class));
    }
}
