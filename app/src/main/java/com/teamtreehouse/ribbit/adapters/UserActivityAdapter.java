package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.viewholders.user.UserActivityVH;
import com.teamtreehouse.ribbit.adapters.viewholders.user.UserViewHolder;

/**
 * Created by javie on 4/4/2017.
 */

public class UserActivityAdapter extends RecyclerAdapter {

    public UserActivityAdapter(RecyclerActivityView parent) {
        super(parent);
    }

    @Override
    protected UserViewHolder getViewHolder(RecyclerActivityView parent, View view) {

        return new UserActivityVH(parent, view);
    }
}
