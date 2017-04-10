package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.DataSnapshot;
import com.teamtreehouse.ribbit.models.UserFriend;

/**
 * Created by javie on 4/9/2017.
 */

public class SelectableFriendsCallback extends FriendsCallback {

    private FriendsListener<UserFriend> listener;

    public SelectableFriendsCallback(FriendsListener listener) {

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
