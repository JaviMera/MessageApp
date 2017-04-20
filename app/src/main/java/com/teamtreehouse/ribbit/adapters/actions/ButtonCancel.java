package com.teamtreehouse.ribbit.adapters.actions;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.InviteStatus;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.database.UpdateInviteCallback;

/**
 * Created by javie on 4/1/2017.
 */

public class ButtonCancel extends ButtonAction {

    @Override
    public void execute(final User user) {

        MessageDB.updateInvites(Auth.getInstance().getUser(), user, InviteStatus.Rejected.ordinal(), new UpdateInviteCallback() {
            @Override
            public void onSuccess() {

                MessageDB.deleteInvites(Auth.getInstance().getUser(), user);
            }
        });
    }
}
