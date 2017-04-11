package com.teamtreehouse.ribbit.ui.fragments.selectfriends;

import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;

import java.util.List;

/**
 * Created by javie on 4/8/2017.
 */

public interface FragmentSuggestionsView extends FragmentRecyclerView {

    void setItems(List<User> users);
}
