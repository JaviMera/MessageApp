package com.teamtreehouse.ribbit.ui.fragments;

import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;

import java.util.List;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentUsersPresenter extends FragmentRecyclerPresenter<FragmentUsersView> {

    public FragmentUsersPresenter(FragmentUsersView view) {
        super(view);
    }

    public void onButtonClick(List<ButtonAction> buttonActions, int position) {

        view.onInviteClick(buttonActions, position);
    }
}
