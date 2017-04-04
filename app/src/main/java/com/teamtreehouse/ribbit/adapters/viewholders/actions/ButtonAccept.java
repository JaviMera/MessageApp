package com.teamtreehouse.ribbit.adapters.viewholders.actions;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 4/1/2017.
 */

public class ButtonAccept extends ButtonAction {

    @Override
    public void execute(User user) {

        MessageDB.insertFriends(Auth.getInstance().getUser(), user);
    }
}
