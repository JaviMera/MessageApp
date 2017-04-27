package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.users.UserSender;

/**
 * Created by javie on 4/6/2017.
 */

public interface SenderListener {

    void onPendingAdded(UserSender user);
    void onPendingRemoved(UserSender user);
}
