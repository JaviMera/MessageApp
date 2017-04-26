package com.teamtreehouse.ribbit.ui.fragments.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.InviteStatus;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserCurrent;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.models.users.UserInvite;
import com.teamtreehouse.ribbit.models.users.UserRequest;
import com.teamtreehouse.ribbit.ui.activities.ActivityView;
import com.teamtreehouse.ribbit.ui.activities.EditFriendActivity;
import com.teamtreehouse.ribbit.ui.callbacks.EditableFriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsListener;
import com.teamtreehouse.ribbit.ui.callbacks.InvitesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.PendingCallback;
import com.teamtreehouse.ribbit.ui.callbacks.RecipientListener;
import com.teamtreehouse.ribbit.ui.callbacks.SenderListener;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersPresenter;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentSearch
    extends
        FragmentRecycler<ActivityView, FragmentUsersPresenter,FragmentSearchAdapter>
    implements
        SenderListener,
        FragmentUsersView,
        RecipientListener,
        FriendsListener<UserFriend> {

    private PendingCallback pendingCallback;
    private InvitesCallback invitesCallback;
    private EditableFriendsCallback editableFriendsCallback;

    private HashMap<String, User> friends = new LinkedHashMap<>();
    private HashMap<String, User> invites = new LinkedHashMap<>();

    public static FragmentSearch newInstance() {

        return new FragmentSearch();
    }

    @Override
    protected FragmentSearchAdapter createAdapter() {

        return new FragmentSearchAdapter(this);
    }

    @Override
    protected FragmentUsersPresenter createPresenter() {

        return new FragmentUsersPresenter(this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {

        return new LinearLayoutManager(this.getContext());
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_recycler;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        pendingCallback = new PendingCallback(this);
        invitesCallback = new InvitesCallback(this);
        editableFriendsCallback = new EditableFriendsCallback(this);
    }

    @Override
    public void onPendingAdded(User user) {

        invites.put(user.getId(), user);

        RecyclerAdapter adapter = getAdapter();
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
        RecyclerAdapter adapter = getAdapter();
        if(adapter.contains(userInvite)) {

            // If so, then update their screen by displaying the sender user as a User Invite type,
            // instead of just showing it as a User Request type
            int position = adapter.getPosition(userInvite);
            adapter.changeItem(userInvite, position);
        }
    }

    @Override
    public void onFriendAdded(UserFriend userFriend) {

        MessageDB.readUser(userFriend.getUsername(), new UserReadCallback() {
            @Override
            public void onUserRead(List<User> users) {

            User user = users.get(0);
            friends.put(user.getId(), new UserFriend(user.getId(), user.getEmail(), user.getUsername(), user.getPhotoUrl()));
            }
        });
    }

    @Override
    public void onFriendRemoved(final UserFriend userFriend) {


        MessageDB.readUser(userFriend.getUsername(), new UserReadCallback() {
            @Override
            public void onUserRead(List<User> users) {

                RecyclerAdapter adapter = getAdapter();
                int position = adapter.getPosition(userFriend);

                User user = users.get(0);
                adapter.changeItem(new UserRequest(user.getId(), user.getEmail(), user.getUsername(), user.getPhotoUrl()), position);
            }
        });

        friends.remove(userFriend.getId());
    }

    public void addUsers(List<User> users){

        UserCurrent userCurrent = (UserCurrent) Auth.getInstance().getUser();
        RecyclerAdapter adapter = getAdapter();

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

    @Override
    public void onInviteChanged(final UserInvite userInvite) {

        final RecyclerAdapter adapter = getAdapter();
        final int position = adapter.getPosition(userInvite);

        MessageDB.readUser(userInvite.getUsername(), new UserReadCallback() {
            @Override
            public void onUserRead(List<User> users) {

                User newUser = users.get(0);

                // When an invitation is accepted, change the current item to a UserFriend type
                if(userInvite.getStatus() == InviteStatus.Accepted.ordinal()) {

                    adapter.changeItem(new UserFriend(newUser), position);
                }
                // When an invitation is declined, change the current item to a UserRequest type
                else if(userInvite.getStatus() == InviteStatus.Rejected.ordinal()) {

                    adapter.changeItem(new UserRequest(newUser.getId(), newUser.getEmail(), newUser.getUsername(), newUser.getPhotoUrl()), position);
                }

                invites.remove(newUser.getId());
            }
        });
    }

    @Override
    public void onInviteClick(List<ButtonAction> buttonActions, int position) {

        FragmentSearchAdapter adapter = getAdapter();
        User user = adapter.getItem(position);

        for(ButtonAction buttonAction : buttonActions) {

            buttonAction.execute(user);
        }
    }

    @Override
    public void onItemSelected(User user) {

        Intent intent = new Intent(this.getActivity(), EditFriendActivity.class);
        intent.putExtra(EditFriendActivity.EDIT_FRIEND_KEY, user);
        this.parent.itemSelect(intent);
    }
}
