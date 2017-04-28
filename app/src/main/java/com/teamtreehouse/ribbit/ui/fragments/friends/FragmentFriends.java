package com.teamtreehouse.ribbit.ui.fragments.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.models.users.UserRecipient;
import com.teamtreehouse.ribbit.ui.activities.ActivityView;
import com.teamtreehouse.ribbit.ui.activities.EditFriendActivity;
import com.teamtreehouse.ribbit.ui.activities.UsersActivity;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsListener;
import com.teamtreehouse.ribbit.ui.callbacks.InvitesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.RecipientListener;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersPresenter;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

import java.util.List;

public class FragmentFriends extends FragmentPager<ActivityView, FragmentUsersPresenter, FragmentFriendsAdapter>
    implements
        FragmentUsersView,
        RecipientListener,
        FriendsListener {

    private InvitesCallback invitesCallback;
    private FriendsCallback friendsCallback;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        User currentUser = Auth.getInstance().getUser();

        this.invitesCallback = new InvitesCallback(currentUser.getId(), this);
        this.friendsCallback = new FriendsCallback(currentUser.getId(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected FragmentFriendsAdapter createAdapter() {

        return new FragmentFriendsAdapter(this);
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
    public void onFriendAdded(final UserFriend userFriend) {

        final RecyclerAdapter adapter = getAdapter();
        MessageDB.readUser(userFriend.getUsername(), new UserReadCallback() {
            @Override
            public void onUserRead(List<User> users) {

                UserFriend newFriend = new UserFriend(users.get(0));
                adapter.add(newFriend);
            }
        });
    }

    @Override
    public void onFriendRemoved(UserFriend userFriend) {

        RecyclerAdapter adapter = getAdapter();
        int position = adapter.getPosition(userFriend);
        adapter.removeItem(position);
    }

    @Override
    public void onInvitesAdded(UserRecipient userInvite) {

        RecyclerAdapter adapter = getAdapter();
        adapter.add(userInvite, 0);
    }

    @Override
    public void execute() {

        Intent intent = new Intent(this.getActivity(), UsersActivity.class);
        startActivity(intent);
    }

    @Override
    public void onInviteRemoved(UserRecipient user) {

        RecyclerAdapter adapter = getAdapter();
        int position = adapter.getPosition(user);
        adapter.removeItem(position);
    }

    @Override
    public void onInviteClick(List<ButtonAction> buttonActions, int position) {

        User currentUser = Auth.getInstance().getUser();
        FragmentFriendsAdapter adapter = (FragmentFriendsAdapter) recyclerView.getAdapter();
        User user = adapter.getItem(position);

        for(ButtonAction buttonAction : buttonActions) {

            buttonAction.execute(currentUser, user);
        }
    }

    @Override
    public void onItemSelected(User user) {

        Intent intent = new Intent(this.getActivity(), EditFriendActivity.class);
        intent.putExtra(EditFriendActivity.EDIT_FRIEND_KEY, user);
        this.parent.itemSelect(intent);
    }
}
