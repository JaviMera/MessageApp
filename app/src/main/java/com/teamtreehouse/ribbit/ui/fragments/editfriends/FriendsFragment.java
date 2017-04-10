package com.teamtreehouse.ribbit.ui.fragments.editfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.InviteStatus;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.ui.activities.ActivityBase;
import com.teamtreehouse.ribbit.ui.activities.UsersActivity;
import com.teamtreehouse.ribbit.ui.callbacks.EditableFriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsListener;
import com.teamtreehouse.ribbit.ui.callbacks.InvitesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.RecipientListener;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersPresenter;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

import java.util.List;

public class FriendsFragment extends FragmentPager<FragmentUsersPresenter, FragmentFriendsAdapter>
    implements
        FragmentUsersView,
        RecipientListener,
        FriendsListener<UserFriend> {

    private InvitesCallback invitesCallback;
    private EditableFriendsCallback editableFriendsCallback;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.invitesCallback = new InvitesCallback(this);
        this.editableFriendsCallback = new EditableFriendsCallback(this);
    }

    @Override
    public void onResume() {
        super.onResume();

//        mCurrentUser = ObsoleteUser.getCurrentUser();
//        mFriendsRelation = mCurrentUser.getRelation(ObsoleteUser.KEY_FRIENDS_RELATION);
//
//        getActivity().setProgressBarIndeterminateVisibility(true);
//
//
//        Query<ObsoleteUser> query = mFriendsRelation.getQuery();
//        query.addAscendingOrder(ObsoleteUser.KEY_USER_ID);
//        query.findInBackground(new FindCallback<ObsoleteUser>() {
//            @Override
//            public void done(List<ObsoleteUser> friends, Exception e) {
//                getActivity().setProgressBarIndeterminateVisibility(false);
//
//                if (e == null) {
//                    mFriends = friends;
//
//                    String[] usernames = new String[mFriends.size()];
//                    int i = 0;
//                    for (ObsoleteUser user : mFriends) {
//                        usernames[i] = user.getUsername();
//                        i++;
//                    }
//                    if (mGridView.createAdapter() == null) {
//                        ObseleteUserAdapter adapter = new ObseleteUserAdapter(getActivity(), mFriends);
//                        mGridView.setAdapter(adapter);
//                    } else {
//                        ((ObseleteUserAdapter) mGridView.createAdapter()).refill(mFriends);
//                    }
//                } else {
//                    Log.e(TAG, e.getMessage());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setMessage(e.getMessage())
//                            .setTitle(R.string.error_title)
//                            .setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//        });
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
    public void onFriendAdded(UserFriend userFriend) {

        RecyclerAdapter adapter = getAdapter();
        int position = adapter.getPosition(userFriend);

        if(position > -1) {

            adapter.removeItem(position);
        }

        adapter.add(userFriend);
    }

    @Override
    public void onFriendRemoved(UserFriend userFriend) {

        RecyclerAdapter adapter = getAdapter();
        int position = adapter.getPosition(userFriend);

        if(position == -1)
            return;

        adapter.removeItem(position);
    }

    @Override
    public void onInvitesAdded(User userInvite) {

        RecyclerAdapter adapter = getAdapter();
        adapter.add(userInvite, 0);
    }

    @Override
    public void execute() {

        Intent intent = new Intent(this.getActivity(), UsersActivity.class);
        startActivity(intent);
    }

    @Override
    public void onInviteChanged(UserInvite user) {

        RecyclerAdapter adapter = getAdapter();
        int position = adapter.getPosition(user);

        if(user.getStatus() == InviteStatus.Accepted.ordinal()) {

            // When an invitation is accepted, change the current item to a UserFriend type
            adapter.changeItem(new UserFriend(user.getId(), user.getUsername()), position);
        }
        else if(user.getStatus() == InviteStatus.Rejected.ordinal()) {

            adapter.removeItem(position);
        }
    }

    @Override
    public void onInviteClick(List<ButtonAction> buttonActions, int position) {

        FragmentFriendsAdapter adapter = (FragmentFriendsAdapter) recyclerView.getAdapter();
        User user = adapter.getItem(position);

        for(ButtonAction buttonAction : buttonActions) {

            buttonAction.execute(user);
        }
    }

    @Override
    public void editFriend(int position) {

        FragmentFriendsAdapter adapter = getAdapter();
        User user = adapter.getItem(position);

        ((ActivityBase)this.getActivity()).itemSelect(user);
    }
}
