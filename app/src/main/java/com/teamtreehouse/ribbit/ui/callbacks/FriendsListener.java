package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.Item;

/**
 * Created by javie on 4/6/2017.
 */

public interface FriendsListener<T extends Item> {

    void onFriendAdded(T userFriend);
    void onFriendRemoved(T userFriend);
}