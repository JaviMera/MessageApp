package com.teamtreehouse.ribbit.adapters.actions;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.InviteStatus;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.database.UpdateInviteCallback;

/**
 * Created by javie on 4/1/2017.
 */

public class ButtonAccept extends ButtonAction {

    @Override
    public void execute(final User user) {

        MessageDB.updateInvites(Auth.getInstance().getUser(), user, InviteStatus.Accepted.ordinal(), new UpdateInviteCallback() {
            @Override
            public void onSuccess() {

                MessageDB.insertFriends(Auth.getInstance().getUser(), user);
                MessageDB.deleteInvites(Auth.getInstance().getUser(), user);
            }
        });
    }
}
