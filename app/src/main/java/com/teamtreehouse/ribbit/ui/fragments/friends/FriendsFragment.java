package com.teamtreehouse.ribbit.ui.fragments.friends;

import android.content.Context;
import android.content.Intent;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.ui.activities.MainActivity;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsers;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersPresenter;

public class FriendsFragment extends FragmentUsers<FragmentUsersPresenter, FragmentFriendsAdapter> {

    private MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
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
//                        ObseleteUserAdapter adapter = new ObseleteUserAdapter(getActivity(), mFriends);
//                        mGridView.setAdapter(adapter);
//                    } else {
//                        ((ObseleteUserAdapter) mGridView.getAdapter()).refill(mFriends);
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
    protected void acceptAdapterAction(UserInvite user, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();

        // When an invitation is accepted, change the current item to a UserFriend type
        adapter.changeItem(new UserFriend(user.getId(), user.getUsername()), position);
    }

    @Override
    protected void rejectAdapterAction(UserInvite user, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();

        // When an invitation is rejected, delete the item from the friends recycler view
        adapter.removeItem(position);
    }

    @Override
    protected FragmentFriendsAdapter getAdapter() {

        return new FragmentFriendsAdapter(this);
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
    public void onFriendAdded(User userFriend) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        int position = adapter.getPosition(userFriend);

        if(position > -1) {

            adapter.removeItem(position);
        }

        adapter.add(userFriend);
    }

    @Override
    public void onFriendRemoved(User userFriend) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        int position = adapter.getPosition(userFriend);
        adapter.removeItem(position);
    }

    @Override
    public void onInvitesAdded(User userInvite) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        adapter.add(userInvite, 0);
    }
}
