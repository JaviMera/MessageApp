package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.DataSnapshot;
import com.teamtreehouse.ribbit.models.UserSelectable;

/**
 * Created by javie on 4/9/2017.
 */

public class SelectableFriendsCallback extends FriendsCallback {

    private FriendsListener<UserSelectable> listener;

    public SelectableFriendsCallback(FriendsListener listener) {

        super();

        this.listener = listener;
    }

    @Override
    public void add(DataSnapshot snapshot) {

        this.listener.onFriendAdded(snapshot.getValue(UserSelectable.class));
    }

    @Override
    public void delete(DataSnapshot snapshot) {

        this.listener.onFriendRemoved(snapshot.getValue(UserSelectable.class));
    }
}
