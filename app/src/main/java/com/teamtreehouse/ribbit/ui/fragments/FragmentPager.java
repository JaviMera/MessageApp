package com.teamtreehouse.ribbit.ui.fragments;

import android.content.Context;

import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.ui.activities.ActivityBase;

/**
 * Created by javie on 4/7/2017.
 */

public abstract class FragmentPager<TActivity extends ActivityBase, TPresenter extends FragmentRecyclerPresenter, TAdapter extends RecyclerAdapter>
    extends
        FragmentRecycler<TActivity, TPresenter, TAdapter> {

    public abstract void execute();
}
