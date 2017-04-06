package com.teamtreehouse.ribbit.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class RecyclerViewHolder<T> extends RecyclerView.ViewHolder{

    public abstract void bind(T item);

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }
}
