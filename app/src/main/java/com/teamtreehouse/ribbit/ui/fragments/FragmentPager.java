package com.teamtreehouse.ribbit.ui.fragments;

import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.ui.activities.ActivityView;

/**
 * Created by javie on 4/7/2017.
 */

public abstract class FragmentPager<TActivity extends ActivityView, TPresenter extends FragmentRecyclerPresenter, TAdapter extends RecyclerAdapter>
    extends
        FragmentRecycler<TActivity, TPresenter, TAdapter> {

    public abstract void execute();
}
