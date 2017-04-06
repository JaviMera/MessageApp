package com.teamtreehouse.ribbit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.RecyclerViewHolder;
import com.teamtreehouse.ribbit.adapters.viewholders.user.UserViewHolder;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 3/27/2017.
 */

public abstract class RecyclerAdapter<T extends User, V extends FragmentRecyclerView> extends RecyclerView.Adapter<RecyclerViewHolder<T>> {

    protected abstract RecyclerViewHolder getViewHolder(V parent, View view);

    private V parent;
    private List<T> items;

    public RecyclerAdapter(V parent) {

        this.parent = parent;
        this.items = new LinkedList<>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return getViewHolder(this.parent, view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

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

    public T getItem(int position) {

        return items.get(position);
    }

    public void changeItem(T item, int position) {

        if(item != null) {

            this.items.set(position, item);
            notifyItemChanged(position);
        }
    }

    public int getPosition(User userFriend) {

        return items.indexOf(userFriend);
    }

    public boolean contains(User user) {

        return this.items.contains(user);
    }

    public void add(T item, int i) {

        items.add(i, item);
        notifyItemInserted(i);
    }

    public void add(T item) {

        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(int position) {

        items.remove(position);
        notifyItemRemoved(position);
    }
}
