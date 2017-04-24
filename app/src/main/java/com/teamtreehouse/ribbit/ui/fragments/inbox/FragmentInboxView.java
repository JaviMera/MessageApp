package com.teamtreehouse.ribbit.ui.fragments.inbox;

import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;

/**
 * Created by javie on 3/25/2017.
 */

public interface FragmentInboxView extends FragmentRecyclerView {

    void setEmptyTextViewVisibility(boolean visible);
    void onItemSelected(Message message);
}
