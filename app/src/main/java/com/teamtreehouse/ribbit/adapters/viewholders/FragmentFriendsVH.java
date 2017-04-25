package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;

import com.google.android.gms.common.UserRecoverableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentUsersVH;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserRequest;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

import java.util.List;

/**
 * Created by javie on 4/4/2017.
 */

public class FragmentFriendsVH extends FragmentUsersVH {

    public FragmentFriendsVH(FragmentUsersView parent, View view) {
        super(parent, view);
    }

    @Override
    protected void setProfilePic(final User user) {

        MessageDB.readUser(user.getUsername(), new UserReadCallback() {
            @Override
            public void onUserRead(List<User> users) {

                User userInfo = users.get(0);
                FragmentFriendsVH.super.setProfilePic(userInfo);
            }
        });
    }
}
