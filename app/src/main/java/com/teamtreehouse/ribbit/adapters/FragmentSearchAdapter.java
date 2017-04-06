package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.viewholders.user.UserActivityVH;
import com.teamtreehouse.ribbit.adapters.viewholders.user.UserViewHolder;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.FragmentUsersView;

/**
 * Created by javie on 4/4/2017.
 */

public class FragmentSearchAdapter extends RecyclerAdapter<User, FragmentUsersView> {

    public FragmentSearchAdapter(FragmentUsersView parent) {
        super(parent);
    }

    @Override
    protected UserViewHolder getViewHolder(FragmentUsersView parent, View view) {

        return new UserActivityVH(parent, view);
    }
}
