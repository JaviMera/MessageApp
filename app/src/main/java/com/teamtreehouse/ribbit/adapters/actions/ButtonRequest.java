package com.teamtreehouse.ribbit.adapters.actions;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.User;

/**
 * Created by javie on 4/2/2017.
 */

public class ButtonRequest extends ButtonAction {

    @Override
    public void execute(User currentUser, User user) {

        MessageDB.insertSenderInvite(currentUser, user);
        MessageDB.insertRecipientInvite(user, currentUser);
    }
}
