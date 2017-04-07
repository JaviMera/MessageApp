package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;

import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.messages.InboxFragmentView;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentMessagesVH extends FragmentRecyclerVH<User> {

    public FragmentMessagesVH(InboxFragmentView parent, View view) {

        super(view);
    }

    @Override
    public void bind(User item) {

    }
}

