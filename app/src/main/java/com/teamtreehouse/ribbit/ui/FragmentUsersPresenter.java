package com.teamtreehouse.ribbit.ui;

import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;

import java.util.List;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentUsersPresenter extends FragmentRecyclerPresenter<FragmentUsersView> {

    FragmentUsersPresenter(FragmentUsersView view) {
        super(view);
    }

    public void onButtonClick(List<ButtonAction> buttonActions, int position) {

        view.onButtonClick(buttonActions, position);
    }
}
