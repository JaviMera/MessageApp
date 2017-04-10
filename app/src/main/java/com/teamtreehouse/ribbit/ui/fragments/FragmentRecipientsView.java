package com.teamtreehouse.ribbit.ui.fragments;

import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 4/10/2017.
 */

public interface FragmentRecipientsView extends FragmentRecyclerView{

    void onFriendAdded(User user);
    void onFriendRemoved(int adapterPosition);
}
