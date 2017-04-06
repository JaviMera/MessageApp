package com.teamtreehouse.ribbit.adapters.viewholders.actions;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.InviteStatus;
import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 4/2/2017.
 */

public class ButtonRequest extends ButtonAction {

    @Override
    public void execute(User user) {

        MessageDB.insertInvite(Auth.getInstance().getUser(), user, InviteStatus.Sent.ordinal());
    }
}
