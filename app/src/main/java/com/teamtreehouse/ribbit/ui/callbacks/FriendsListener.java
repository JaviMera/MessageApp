package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.Item;
import com.teamtreehouse.ribbit.models.users.UserFriend;

/**
 * Created by javie on 4/6/2017.
 */

public interface FriendsListener {

    void onFriendAdded(UserFriend userFriend);
    void onFriendRemoved(UserFriend userFriend);
}