package com.teamtreehouse.ribbit.ui.fragments;

import android.support.v4.app.Fragment;

import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;

/**
 * Created by javie on 4/7/2017.
 */

public abstract class FragmentPager<P extends FragmentRecyclerPresenter,A extends RecyclerAdapter>
    extends
        FragmentRecycler<P,A> {

    public abstract void execute();
}
