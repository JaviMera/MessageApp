package com.teamtreehouse.ribbit.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerActivityView;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.UserAdapter;
import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.purgatory.ObsoleteUser;
import com.teamtreehouse.ribbit.models.purgatory.Query;
import com.teamtreehouse.ribbit.models.purgatory.Relation;
import com.teamtreehouse.ribbit.models.callbacks.FindCallback;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.InvitesCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsFragment extends Fragment
    implements
    RecyclerActivityView,
    InvitesCallback.OnInviteListener,
    FriendsCallback.OnFriendsListener{

    private InvitesCallback invitesCallback;
    private FriendsCallback friendsCallback;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_grid,
                container, false);

        ButterKnife.bind(this, rootView);

        RecyclerAdapter adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.invitesCallback = new InvitesCallback(this);
        this.friendsCallback = new FriendsCallback(this);
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
//                    if (mGridView.getAdapter() == null) {
//                        UserAdapter adapter = new UserAdapter(getActivity(), mFriends);
//                        mGridView.setAdapter(adapter);
//                    } else {
//                        ((UserAdapter) mGridView.getAdapter()).refill(mFriends);
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
    public void onButtonClick(List<ButtonAction> buttonActions, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        User user = adapter.getItem(position);

        for(ButtonAction buttonAction : buttonActions) {

            buttonAction.execute(user);
        }
    }

    @Override
    public void onRemoveItem(int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        adapter.removeItem(position);
    }

    @Override
    public void onChangeItem(User user, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        adapter.changeItem(user, position);
    }

    @Override
    public void onFriendAdded(User userFriend) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        adapter.addUser(userFriend);
    }

    @Override
    public void onInvitesAdded(User userInvite) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        adapter.addUser(userInvite);
    }

    @Override
    public void onInviteRemoved(User userInvite) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        int position = adapter.getPosition(userInvite);
        adapter.removeItem(position);
    }
}
