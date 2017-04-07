package com.teamtreehouse.ribbit.ui.fragments.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserCurrent;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.models.UserRequest;
import com.teamtreehouse.ribbit.ui.callbacks.PendingCallback;
import com.teamtreehouse.ribbit.ui.callbacks.SenderListener;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsers;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersPresenter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentSearch extends FragmentUsers<FragmentUsersPresenter,FragmentSearchAdapter>
    implements
    SenderListener{

    private PendingCallback pendingCallback;

    private HashMap<String, User> friends = new LinkedHashMap<>();
    private HashMap<String, User> invites = new LinkedHashMap<>();

    public static FragmentSearch newInstance() {

        return new FragmentSearch();
    }

    @Override
    protected void acceptAdapterAction(UserInvite user, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();

        // When an invitation is accepted, change the current item to a UserFriend type
        adapter.changeItem(new UserFriend(user.getId(), user.getUsername()), position);
    }

    @Override
    protected void rejectAdapterAction(UserInvite user, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();

        // When an invitation is rejected, change the current item to a User Request type
        adapter.changeItem(new UserRequest(user.getId(), user.getUsername()), position);
    }

    @Override
    protected FragmentSearchAdapter getAdapter() {

        return new FragmentSearchAdapter(this);
    }

    @Override
    protected FragmentUsersPresenter getPresenter() {

        return new FragmentUsersPresenter(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        pendingCallback = new PendingCallback(this);
    }

    @Override
    public void onPendingAdded(User user) {

        invites.put(user.getId(), user);

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        int position = adapter.getPosition(user);

        if(position == -1)
            return;

        if(adapter.getItem(position) instanceof UserFriend)
            return;

        // When an invitation is sent, change the current item to a UserSender type
        adapter.changeItem(user, position);
    }

    @Override
    public void onInvitesAdded(User userInvite) {

        invites.put(userInvite.getId(), userInvite);

        // When the current user sends an invite, check if the receiver of this invite
        // also happens to be looking for the sender user.
        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        if(adapter.contains(userInvite)) {

            // If so, then update their screen by displaying the sender user as a User Invite type,
            // instead of just showing it as a User Request type
            int position = adapter.getPosition(userInvite);
            adapter.changeItem(userInvite, position);
        }
    }

    @Override
    public void onFriendAdded(User userFriend) {

        friends.put(userFriend.getId(), userFriend);
    }

    @Override
    public void onFriendRemoved(User userFriend) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        int position = adapter.getPosition(userFriend);

        adapter.changeItem(new UserRequest(userFriend.getId(), userFriend.getUsername()), position);
    }

    public void addUsers(List<User> users){

        UserCurrent userCurrent = (UserCurrent) Auth.getInstance().getUser();
        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();

        // For every new letter the user types in, clear the recycler to show the newest search result
        adapter.clear();

        for(User user : users) {

            if(user.getId().equals(userCurrent.getId()))
                continue;

            if (friends.containsKey(user.getId())) {

                adapter.add(friends.get(user.getId()));

            } else if (invites.containsKey(user.getId())) {

                adapter.add(invites.get(user.getId()));

            } else {

                adapter.add(user);
            }
        }
    }
}
