package com.teamtreehouse.ribbit.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teamtreehouse.ribbit.models.Item;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class FragmentRecyclerVH<T extends Item> extends RecyclerView.ViewHolder{

    public abstract void bind(T item);

    public FragmentRecyclerVH(View itemView) {
        super(itemView);
    }
}
