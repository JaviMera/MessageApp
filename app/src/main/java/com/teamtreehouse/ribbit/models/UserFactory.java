package com.teamtreehouse.ribbit.models;

import com.teamtreehouse.ribbit.adapters.RecyclerItemType;

/**
 * Created by javie on 4/2/2017.
 */

public class UserFactory {

    public RecyclerItemType getViewHolder(User user) {

        if(user instanceof UserFriend) {

            return new ItemFriend();
        }
        else if(user instanceof UserSender) {

            return new ItemPending();
        }
        else if(user instanceof UserRecipient) {

            return new ItemInvite();
        }

        return new ItemRequest();
    }
}
