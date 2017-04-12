package com.teamtreehouse.ribbit.ui.fragments;

import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.User;

import java.util.List;

/**
 * Created by javie on 4/6/2017.
 */

public interface FragmentUsersView extends FragmentRecyclerView {

    void onInviteClick(List<ButtonAction> buttonActions, int position);
    void onItemSelected(User user);
}
