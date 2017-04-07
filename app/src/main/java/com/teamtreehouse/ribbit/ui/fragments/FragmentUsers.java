package com.teamtreehouse.ribbit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.InviteStatus;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsListener;
import com.teamtreehouse.ribbit.ui.callbacks.InvitesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.RecipientListener;

import java.util.List;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class FragmentUsers<P extends FragmentRecyclerPresenter,A extends RecyclerAdapter>
    extends
        FragmentRecycler<P, A>
    implements
        FragmentUsersView,
        RecipientListener,
        FriendsListener {

    protected abstract void acceptAdapterAction(UserInvite user, int position);
    protected abstract void rejectAdapterAction(UserInvite user, int position);

    private InvitesCallback invitesCallback;
    private FriendsCallback friendsCallback;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.invitesCallback = new InvitesCallback(this);
        this.friendsCallback = new FriendsCallback(this);
    }

    @Override
    public void onInviteClick(List<ButtonAction> buttonActions, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        User user = adapter.getItem(position);

        for(ButtonAction buttonAction : buttonActions) {

            buttonAction.execute(user);
        }
    }

    @Override
    public void onInviteChanged(UserInvite user) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        int position = adapter.getPosition(user);

        if(user.getStatus() == InviteStatus.Accepted.ordinal()) {

            // When an invitation is accepted, change the current item to a UserFriend type
            acceptAdapterAction(user, position);
        }
        else if(user.getStatus() == InviteStatus.Rejected.ordinal()) {

            rejectAdapterAction(user, position);
        }
    }

    @Override
    public void editFriend(int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        User user = adapter.getItem(position);

        this.activity.editFriend(user);
    }
}
