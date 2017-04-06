package com.teamtreehouse.ribbit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teamtreehouse.ribbit.adapters.viewholders.InboxViewHolder;
import com.teamtreehouse.ribbit.adapters.viewholders.RecyclerViewHolder;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.FragmentUsersView;
import com.teamtreehouse.ribbit.ui.InboxFragmentView;

/**
 * Created by javie on 4/6/2017.
 */

public class InboxFragmentAdapter extends RecyclerAdapter<User, InboxFragmentView>{

    public InboxFragmentAdapter(InboxFragmentView parent) {
        super(parent);
    }

    @Override
    protected RecyclerViewHolder getViewHolder(InboxFragmentView parent, View view) {

        return new InboxViewHolder(parent, view);
    }
}
