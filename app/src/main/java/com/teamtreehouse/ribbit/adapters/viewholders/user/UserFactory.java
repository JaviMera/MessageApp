package com.teamtreehouse.ribbit.adapters.viewholders.user;

import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.models.UserPending;

/**
 * Created by javie on 4/2/2017.
 */

public class UserFactory {

    public RecyclerItemType getViewHolder(User user) {

        if(user instanceof UserFriend) {

            return new ItemFriend();
        }
        else if(user instanceof UserPending) {

            return new ItemPending();
        }
        else if(user instanceof UserInvite) {

            return new ItemInvite();
        }

        return new ItemRequest();
    }
}
