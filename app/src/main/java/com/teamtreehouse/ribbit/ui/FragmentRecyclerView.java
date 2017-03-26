package com.teamtreehouse.ribbit.ui;

import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.adapters.MessageAdapter;

/**
 * Created by javie on 3/25/2017.
 */

public interface FragmentRecyclerView extends MessageAdapter.OnRecyclerViewClick {

    void setRecyclerAdapter(RecyclerView.Adapter adapter);
    void setRecyclerLayout(RecyclerView.LayoutManager layoutManager);
    void setRecyclerFixedSize(boolean fixedSize);
}
