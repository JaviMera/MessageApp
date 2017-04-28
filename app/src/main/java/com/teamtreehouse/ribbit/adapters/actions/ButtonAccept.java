package com.teamtreehouse.ribbit.adapters.actions;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.callbacks.InviteListener;

/**
 * Created by javie on 4/1/2017.
 */

public class ButtonAccept extends ButtonAction {

    @Override
    public void execute(final User currentUser, final User user) {

        MessageDB.deleteInvites(currentUser, user, new InviteListener() {
            @Override
            public void onInviteDeleted() {

                MessageDB.insertFriends(currentUser, user);
            }
        });
    }
}
