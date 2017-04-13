package com.teamtreehouse.ribbit.ui.fragments.recipients;

import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;

/**
 * Created by javie on 4/10/2017.
 */

public interface FragmentRecipientsView extends FragmentRecyclerView {

    void onFriendAdded(User user);
    void onFriendRemoved(int adapterPosition);
    void onItemSelected(User user);
}
