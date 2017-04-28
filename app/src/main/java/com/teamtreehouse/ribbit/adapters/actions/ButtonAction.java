package com.teamtreehouse.ribbit.adapters.actions;

import com.teamtreehouse.ribbit.models.users.User;

/**
 * Created by javie on 3/30/2017.
 */

public abstract class ButtonAction {

    public abstract void execute(final User currentUser, final User user);
}
