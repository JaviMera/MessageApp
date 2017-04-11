package com.teamtreehouse.ribbit.ui.fragments.inbox;

import com.teamtreehouse.ribbit.models.Item;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;

/**
 * Created by javie on 3/25/2017.
 */

public interface FragmentMessagesView<TItem extends Item> extends FragmentRecyclerView<TItem> {

    void setEmptyTextViewVisibility(boolean visible);
    void setRefresherColors(int[] colors);
    void setRefresherListener();
}
