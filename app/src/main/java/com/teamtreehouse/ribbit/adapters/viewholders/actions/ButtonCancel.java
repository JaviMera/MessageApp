package com.teamtreehouse.ribbit.adapters.viewholders.actions;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.callbacks.InviteDeleteCallback;
import com.teamtreehouse.ribbit.ui.callbacks.UpdateInviteCallback;

/**
 * Created by javie on 4/1/2017.
 */

public class ButtonCancel extends ButtonAction {

    @Override
    public void execute(final User user) {

        MessageDB.updateInvites(Auth.getInstance().getUser(), user, 2, new UpdateInviteCallback() {
            @Override
            public void onSuccess() {

                MessageDB.deleteInvites(Auth.getInstance().getUser(), user);
            }
        });
    }
}
