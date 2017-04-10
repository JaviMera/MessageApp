package com.teamtreehouse.ribbit.ui.fragments.editfriends;

import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentFriendsVH;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentUsersVH;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

/**
 * Created by javie on 4/4/2017.
 */

public class FragmentFriendsAdapter extends RecyclerAdapter<User, FragmentUsersView> {

    public FragmentFriendsAdapter(FragmentUsersView parent) {
        super(parent);
    }

    @Override
    protected FragmentUsersVH getViewHolder(FragmentUsersView parent, View view) {

        return new FragmentFriendsVH(parent, view);
    }

    @Override
    protected int getItemLayout() {

        return R.layout.recycler_item;
    }
}
