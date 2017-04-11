package com.teamtreehouse.ribbit.ui.fragments;

import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.adapters.MessageAdapter;
import com.teamtreehouse.ribbit.models.Item;
import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 3/25/2017.
 */

public interface FragmentRecyclerView<TItem extends Item> {

    void setRecyclerAdapter(RecyclerView.Adapter adapter);
    void setRecyclerLayout(RecyclerView.LayoutManager layoutManager);
    void setRecyclerFixedSize(boolean fixedSize);
    void onItemSelected(User user);
    void whyYouNoWorkMethod(TItem item);
}
