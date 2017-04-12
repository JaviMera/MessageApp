package com.teamtreehouse.ribbit.ui.fragments.messages;

import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerPresenter;

/**
 * Created by javie on 3/25/2017.
 */

public class FragmentMessagesPresenter extends FragmentRecyclerPresenter<FragmentMessagesView> {

    public FragmentMessagesPresenter(FragmentMessagesView view) {
        super(view);
    }

    public void setEmptyTextViewVisibility(boolean visible) {

        view.setEmptyTextViewVisibility(visible);
    }

    public void setRefresherTheme(int[] colors) {

        view.setRefresherColors(colors);
    }

    public void setRefresherListener() {


    }
}