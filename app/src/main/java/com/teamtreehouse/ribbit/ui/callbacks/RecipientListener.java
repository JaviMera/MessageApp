package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.users.User;

/**
 * Created by javie on 4/6/2017.
 */

public interface RecipientListener extends InvitesListener{

    void onInvitesAdded(User userInvite);
}
