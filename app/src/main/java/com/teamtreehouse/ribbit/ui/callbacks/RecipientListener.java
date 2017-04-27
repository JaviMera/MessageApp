package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.users.UserRecipient;

/**
 * Created by javie on 4/6/2017.
 */

public interface RecipientListener{

    void onInvitesAdded(UserRecipient userInvite);
    void onInviteRemoved(UserRecipient userInvite);
}
