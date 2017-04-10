package com.teamtreehouse.ribbit.ui.fragments;

import android.support.v7.widget.RecyclerView;

/**
 * Created by javie on 3/25/2017.
 */

public abstract class FragmentRecyclerPresenter<T extends FragmentRecyclerView> {

    protected T view;

    public FragmentRecyclerPresenter(T view) {

        this.view = view;
    }

    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {

        this.view.setRecyclerAdapter(adapter);
    }

    public void setRecyclerLayout(RecyclerView.LayoutManager layoutManager) {

        this.view.setRecyclerLayout(layoutManager);
    }

    public void setRecyclerFixedSize(boolean fixedSize) {

        this.view.setRecyclerFixedSize(fixedSize);
    }
}
