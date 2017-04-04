package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;

import java.util.HashMap;

/**
 * Created by javie on 4/4/2017.
 */

public class UserFilterCallback {

    private final UserListener listener;

    public interface UserListener {

        void onUserAdded(User user);
    }

    public UserFilterCallback(UserListener listener) {

        this.listener = listener;
    }

    public void filterUsers(String username, final HashMap<String, User> friends, final HashMap<String, User> invites) {

        if(username.isEmpty())
            return;

        MessageDB.filterUsers(username, new UserReadCallback() {
            @Override
            public void onUserRead(User user) {

            if(!user.getId().equals(Auth.getInstance().getId())){

                if(friends.containsKey(user.getId())) {

                    listener.onUserAdded(friends.get(user.getId()));
                }
                else if(invites.containsKey(user.getId())){

                    listener.onUserAdded(invites.get(user.getId()));
                }
                else {

                    listener.onUserAdded(user);
                }
            }

            }
        });
    }
}
