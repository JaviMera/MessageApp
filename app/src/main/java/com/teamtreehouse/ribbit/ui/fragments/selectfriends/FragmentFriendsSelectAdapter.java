package com.teamtreehouse.ribbit.ui.fragments.selectfriends;

import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentFriendsSelectVH;
import com.teamtreehouse.ribbit.models.UserSelectable;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentFriendsSelectAdapter extends RecyclerAdapter<UserSelectable, FragmentFriendsSelectView> {

    public FragmentFriendsSelectAdapter(FragmentFriendsSelectView parent) {
        super(parent);
    }

    @Override
    protected FragmentFriendsSelectVH getViewHolder(FragmentFriendsSelectView parent, View view) {

        return new FragmentFriendsSelectVH(parent, view);
    }

    @Override
    protected int getItemLayout() {

        return R.layout.friends_select_item;
    }
}
