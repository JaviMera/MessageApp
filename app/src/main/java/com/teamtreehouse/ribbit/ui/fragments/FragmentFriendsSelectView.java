package com.teamtreehouse.ribbit.ui.fragments;

import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserSelectable;

import java.util.List;

/**
 * Created by javie on 4/8/2017.
 */

public interface FragmentFriendsSelectView extends FragmentRecyclerView {

    void onFriendSelected(UserSelectable friend);
    void onFriendDeselected(UserSelectable friend);
    List<User> getRecipients();
}
