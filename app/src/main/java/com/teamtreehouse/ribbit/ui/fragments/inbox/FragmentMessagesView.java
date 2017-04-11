package com.teamtreehouse.ribbit.ui.fragments.inbox;

import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;

/**
 * Created by javie on 3/25/2017.
 */

public interface FragmentMessagesView extends FragmentRecyclerView {

    void setEmptyTextViewVisibility(boolean visible);
    void setRefresherColors(int[] colors);
    void setRefresherListener();
}
