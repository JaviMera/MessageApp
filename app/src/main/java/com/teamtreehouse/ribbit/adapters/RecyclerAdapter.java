package com.teamtreehouse.ribbit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.user.ViewHolderUser;
import com.teamtreehouse.ribbit.models.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 3/27/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolderUser> {

    public void addUser(User user) {

        users.add(user);
        notifyItemInserted(users.size() - 1);
    }

    public void removeItem(int position) {

        users.remove(position);
        notifyItemRemoved(position);
    }

    private RecyclerActivityView parent;
    private List<User> users;

    public RecyclerAdapter(RecyclerActivityView parent) {

        this.parent = parent;
        this.users = new LinkedList<>();
    }

    @Override
    public ViewHolderUser onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolderUser(this.parent, view);
    }

    @Override
    public void onBindViewHolder(ViewHolderUser holder, int position) {

        holder.bind(this.users.get(position));
    }

    @Override
    public int getItemCount() {

        return this.users.size();
    }

    public void clear() {

        int size = users.size();
        users.clear();
        notifyItemRangeRemoved(0, size);
    }

    public User getItem(int position) {

        return users.get(position);
    }

    public void changeItem(User user, int position) {

        if(user != null) {

            this.users.set(position, user);
            notifyItemChanged(position);
        }
    }

    public int getPosition(User userFriend) {

        return users.indexOf(userFriend);
    }

    public boolean contains(User user) {

        return this.users.contains(user);
    }
}
