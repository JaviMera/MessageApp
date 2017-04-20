package com.teamtreehouse.ribbit.models;

import com.teamtreehouse.ribbit.adapters.ItemFriend;
import com.teamtreehouse.ribbit.adapters.ItemInvite;
import com.teamtreehouse.ribbit.adapters.ItemPending;
import com.teamtreehouse.ribbit.adapters.ItemRequest;
import com.teamtreehouse.ribbit.adapters.RecyclerItemType;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.models.users.UserRecipient;
import com.teamtreehouse.ribbit.models.users.UserSender;

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
