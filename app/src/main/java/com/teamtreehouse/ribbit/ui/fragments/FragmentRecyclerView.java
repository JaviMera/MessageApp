package com.teamtreehouse.ribbit.ui.fragments;

import android.support.v7.widget.RecyclerView;

/**
 * Created by javie on 3/25/2017.
 */

public interface FragmentRecyclerView {

    void setRecyclerAdapter(RecyclerView.Adapter adapter);
    void setRecyclerLayout(RecyclerView.LayoutManager layoutManager);
    void setRecyclerFixedSize(boolean fixedSize);
}
