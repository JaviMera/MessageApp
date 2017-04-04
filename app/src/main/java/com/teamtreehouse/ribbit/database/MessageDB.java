package com.teamtreehouse.ribbit.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserCurrent;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.models.UserPending;
import com.teamtreehouse.ribbit.models.UserRequest;
import com.teamtreehouse.ribbit.ui.callbacks.InviteDeleteCallback;
import com.teamtreehouse.ribbit.ui.callbacks.UserInsertCallback;
import com.teamtreehouse.ribbit.ui.callbacks.UserReadCallback;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by javie on 4/4/2017.
 */

public class MessageDB {

    public static final String USERS_NODE = "users";
    public static final String INVITES_NODE = "invites";
    public static final String RECIPIENTS_NODE = "recipients";
    public static final String SENDERS_NODE = "senders";
    public static final String FRIENDS_NODE = "friends";
    public static final String USERNAME_PROP = "username";

    public static void insertUser(final User newUser, final UserInsertCallback listener) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(USERS_NODE)
            .push()
            .runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {

                    mutableData.setValue(newUser);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                    if(databaseError == null){

                        listener.onSuccess(newUser);
                    }
                    else {

                        listener.onFailure(databaseError.getMessage());
                    }
                }
            });
    }

    public static void insertInvite(final User currentUser, final User otherUser) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .child(RECIPIENTS_NODE)
            .child(otherUser.getId())
            .push()
            .setValue(new UserInvite(currentUser.getId(), currentUser.getUsername()));

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .child(SENDERS_NODE)
            .child(currentUser.getId())
            .push()
            .setValue(new UserPending(otherUser.getId(), otherUser.getUsername()));
    }

    public static void readUser(final String username, final UserReadCallback userReadCallback) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(USERS_NODE)
            .orderByChild("username")
            .equalTo(username)
            .addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getChildren().iterator().next().getValue(UserCurrent.class);
                    userReadCallback.onUserRead(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public static void filterUsers(final String username, final UserReadCallback filterUserCallback) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(USERS_NODE)
            .orderByChild("username")
            .startAt(username)
            .endAt(username+"\uf8ff")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {

                        filterUserCallback.onUserRead(ds.getValue(UserRequest.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public static void readSenderInvites(String userId, ChildEventListener listener) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .child(RECIPIENTS_NODE)
            .child(userId)
            .addChildEventListener(listener);
    }

    public static void readRecipientInvites(String userId, ChildEventListener listener) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .child(SENDERS_NODE)
            .child(userId)
            .addChildEventListener(listener);
    }

    public static void readFriends(String userId, ChildEventListener listener) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(FRIENDS_NODE)
            .child(userId)
            .addChildEventListener(listener);
    }

    public static void deleteInvites(User currentUser, User otherUser, final InviteDeleteCallback inviteDeleteCallback) {

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put(RECIPIENTS_NODE + "/" + currentUser.getId(), otherUser.getUsername());
        map.put(SENDERS_NODE + "/" + otherUser.getId(), currentUser.getUsername());

        FirebaseDatabase.getInstance().getReference()
            .child(INVITES_NODE)
            .runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {

                    for(Map.Entry<String, String> entry : map.entrySet()) {

                        for(MutableData md : mutableData.child(entry.getKey()).getChildren()) {

                            HashMap<String, String> childMap = (HashMap<String, String>) md.getValue();
                            if(childMap.get(USERNAME_PROP).equals(entry.getValue())) {
                                md.setValue(null);
                                break;
                            }
                        }
                    }

                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                    if(databaseError == null) {

                        inviteDeleteCallback.onInviteDeleted();
                    }
                }
            });
    }

    public static void insertFriends(User currentUser, User otherUser) {

        FirebaseDatabase.getInstance().getReference()
            .child("friends")
            .child(currentUser.getId())
            .push()
            .setValue(new UserFriend(otherUser.getId(), otherUser.getUsername()));

        FirebaseDatabase.getInstance().getReference()
            .child("friends")
            .child(otherUser.getId())
            .push()
            .setValue(new UserFriend(currentUser.getId(), currentUser.getUsername()));
    }
}
