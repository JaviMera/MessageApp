package com.teamtreehouse.ribbit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.adapters.viewholders.FragmentRecyclerVH;
import com.teamtreehouse.ribbit.models.Item;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 3/27/2017.
 */

public abstract class RecyclerAdapter<TItem extends Item, TView extends FragmentRecyclerView>
    extends
        RecyclerView.Adapter<FragmentRecyclerVH<TView, TItem>> {

    protected TView parent;
    protected abstract FragmentRecyclerVH getViewHolder(TView parent, View view);
    protected abstract int getItemLayout();

    protected List<TItem> items;

    public RecyclerAdapter(TView parent) {

        this.parent = parent;
        this.items = new LinkedList<>();
    }

    @Override
    public FragmentRecyclerVH<TView, TItem> onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return getViewHolder(this.parent, view);
    }

    @Override
    public void onBindViewHolder(FragmentRecyclerVH holder, int position) {

        holder.bind(this.items.get(position));
    }

    @Override
    public int getItemCount() {

        return this.items.size();
    }

    public void clear() {

        int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }

    public TItem getItem(int position) {

        return items.get(position);
    }

    public void changeItem(TItem item, int position) {

        if(item != null) {

            this.items.set(position, item);
            notifyItemChanged(position);
        }
    }

    public int getPosition(TItem item) {

        return items.indexOf(item);
    }

    public boolean contains(User user) {

        return this.items.contains(user);
    }

    public void add(TItem item, int i) {

        items.add(i, item);
        notifyItemInserted(i);
    }

    public void add(TItem item) {

        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(int position) {

        items.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(List<TItem> users) {

        items.addAll(users);
        notifyItemRangeInserted(0, items.size());
    }
}
