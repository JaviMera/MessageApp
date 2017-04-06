package com.teamtreehouse.ribbit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.user.UserViewHolder;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.FragmentUsersView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 3/27/2017.
 */

public abstract class RecyclerAdapter extends RecyclerView.Adapter<UserViewHolder> {

    protected abstract UserViewHolder getViewHolder(FragmentUsersView parent, View view);

    private FragmentUsersView parent;
    private List<User> items;

    public RecyclerAdapter(FragmentUsersView parent) {

        this.parent = parent;
        this.items = new LinkedList<>();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return getViewHolder(this.parent, view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

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

    public User getItem(int position) {

        return items.get(position);
    }

    public void changeItem(User user, int position) {

        if(user != null) {

            this.items.set(position, user);
            notifyItemChanged(position);
        }
    }

    public int getPosition(User userFriend) {

        return items.indexOf(userFriend);
    }

    public boolean contains(User user) {

        return this.items.contains(user);
    }

    public void addUser(User userInvite, int i) {

        items.add(i, userInvite);
        notifyItemInserted(i);
    }

    public void addUser(User user) {

        items.add(user);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(int position) {

        items.remove(position);
        notifyItemRemoved(position);
    }
}
