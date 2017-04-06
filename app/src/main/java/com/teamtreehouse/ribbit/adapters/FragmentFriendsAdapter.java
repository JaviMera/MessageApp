package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.viewholders.user.FragmentFriendsVH;
import com.teamtreehouse.ribbit.adapters.viewholders.user.UserViewHolder;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.FragmentUsersView;

/**
 * Created by javie on 4/4/2017.
 */

public class FragmentFriendsAdapter extends RecyclerAdapter<User, FragmentUsersView> {

    public FragmentFriendsAdapter(FragmentUsersView parent) {
        super(parent);
    }

    @Override
    protected UserViewHolder getViewHolder(FragmentUsersView parent, View view) {

        return new FragmentFriendsVH(parent, view);
    }
}
