package com.teamtreehouse.ribbit.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserSelectable;
import com.teamtreehouse.ribbit.ui.callbacks.EditableFriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsListener;
import com.teamtreehouse.ribbit.ui.callbacks.SelectableFriendsCallback;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentFriendsSelect
    extends
        FragmentRecycler<FragmentFriendsSelectPresenter, FragmentFriendsSelectAdapter>
    implements
        FragmentFriendsSelectView, FriendsListener<UserSelectable> {

    public static FragmentFriendsSelect newInstance() {

        return new FragmentFriendsSelect();
    }

    private List<User> recipients;
    private SelectableFriendsCallback selectableFriendsCallback;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.recipients = new LinkedList<>();
        this.selectableFriendsCallback = new SelectableFriendsCallback(this);
    }

    @Override
    protected FragmentFriendsSelectAdapter getAdapter() {

        return new FragmentFriendsSelectAdapter(this);
    }

    @Override
    protected FragmentFriendsSelectPresenter getPresenter() {

        return new FragmentFriendsSelectPresenter(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_recycler;
    }

    @Override
    public void onFriendAdded(UserSelectable userFriend) {

        FragmentFriendsSelectAdapter adapter = (FragmentFriendsSelectAdapter) this.recyclerView.getAdapter();
        adapter.add(userFriend);
    }

    @Override
    public void onFriendRemoved(UserSelectable userFriend) {

    }

    @Override
    public void onFriendSelected(UserSelectable friend) {

        this.recipients.add(friend);
    }

    @Override
    public void onFriendDeselected(UserSelectable friend) {

        this.recipients.remove(friend);
    }

    @Override
    public List<User> getRecipients() {

        return this.recipients;
    }
}
