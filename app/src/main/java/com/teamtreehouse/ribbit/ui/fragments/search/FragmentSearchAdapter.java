package com.teamtreehouse.ribbit.ui.fragments.search;

import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentSearchVH;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentUsersVH;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

/**
 * Created by javie on 4/4/2017.
 */

public class FragmentSearchAdapter extends RecyclerAdapter<User, FragmentUsersView> {

    public FragmentSearchAdapter(FragmentUsersView parent) {
        super(parent);
    }

    @Override
    protected FragmentUsersVH getViewHolder(FragmentUsersView parent, View view) {

        return new FragmentSearchVH(parent, view);
    }

    @Override
    protected int getItemLayout() {

        return R.layout.recycler_item;
    }
}
