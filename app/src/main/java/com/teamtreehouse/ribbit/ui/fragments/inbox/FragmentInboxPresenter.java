package com.teamtreehouse.ribbit.ui.fragments.inbox;

import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerPresenter;

/**
 * Created by javie on 3/25/2017.
 */

public class FragmentInboxPresenter extends FragmentRecyclerPresenter<FragmentInboxView> {

    public FragmentInboxPresenter(FragmentInboxView view) {
        super(view);
    }

    public void setEmptyTextViewVisibility(boolean visible) {

        view.setEmptyTextViewVisibility(visible);
    }
}
