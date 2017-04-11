package com.teamtreehouse.ribbit.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teamtreehouse.ribbit.models.Item;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class FragmentRecyclerVH<TFragment extends FragmentRecyclerView, T extends Item> extends RecyclerView.ViewHolder{

    protected TFragment fragment;
    public abstract void bind(T item);

    public FragmentRecyclerVH(TFragment fragment, View itemView) {
        super(itemView);
        this.fragment = fragment;
    }
}
