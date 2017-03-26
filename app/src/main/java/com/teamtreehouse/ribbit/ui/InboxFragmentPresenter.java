package com.teamtreehouse.ribbit.ui;

/**
 * Created by javie on 3/25/2017.
 */

public class InboxFragmentPresenter extends FragmentRecyclerPresenter<InboxFragmentView> {

    InboxFragmentPresenter(InboxFragmentView view) {
        super(view);
    }

    public void setEmptyTextViewVisibility(boolean visible) {

        view.setEmptyTextViewVisibility(visible);
    }
}
