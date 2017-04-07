package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.viewholders.FragmentMessagesVH;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentRecyclerVH;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.messages.InboxFragmentView;

/**
 * Created by javie on 4/6/2017.
 */

public class InboxFragmentAdapter extends RecyclerAdapter<User, InboxFragmentView>{

    public InboxFragmentAdapter(InboxFragmentView parent) {
        super(parent);
    }

    @Override
    protected FragmentRecyclerVH getViewHolder(InboxFragmentView parent, View view) {

        return new FragmentMessagesVH(parent, view);
    }
}
