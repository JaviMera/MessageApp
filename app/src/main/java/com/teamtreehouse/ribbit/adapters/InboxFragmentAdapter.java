package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentMessagesVH;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentRecyclerVH;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.inbox.InboxFragmentView;

/**
 * Created by javie on 4/6/2017.
 */

public class InboxFragmentAdapter extends RecyclerAdapter<Message, InboxFragmentView>{

    public InboxFragmentAdapter(InboxFragmentView parent) {
        super(parent);
    }

    @Override
    protected FragmentRecyclerVH getViewHolder(InboxFragmentView parent, View view) {

        return new FragmentMessagesVH(parent, view);
    }

    @Override
    protected int getItemLayout() {

        return R.layout.message_item;
    }
}
