package com.teamtreehouse.ribbit.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserCurrent;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserRecipient;
import com.teamtreehouse.ribbit.models.UserSender;
import com.teamtreehouse.ribbit.models.UserRequest;
import com.teamtreehouse.ribbit.ui.callbacks.UpdateInviteCallback;
import com.teamtreehouse.ribbit.ui.callbacks.UserInsertCallback;
import com.teamtreehouse.ribbit.ui.callbacks.UserReadCallback;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    public static final String MESSAGES_NODE = "messages";
    public static final String TEXT_MESSAGES_NODE = "text";

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

    public static void insertInvite(final User currentUser, final User otherUser, int status) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .child(RECIPIENTS_NODE)
            .child(otherUser.getId())
            .push()
            .setValue(new UserRecipient(currentUser.getId(), currentUser.getUsername(), status));

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .child(SENDERS_NODE)
            .child(currentUser.getId())
            .push()
            .setValue(new UserSender(otherUser.getId(), otherUser.getUsername(), status));
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

                    final User user = dataSnapshot.getChildren().iterator().next().getValue(UserCurrent.class);
                    userReadCallback.onUserRead(new LinkedList<User>(){{add(user);}});
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

                    List<User> users = new LinkedList<User>();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {

                        users.add(ds.getValue(UserRequest.class));
                    }

                    filterUserCallback.onUserRead(users);
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

    public static void updateInvites(final User currentUser, final User otherUser, final int status, final UpdateInviteCallback cb) {

        final HashMap<String, User> map = new HashMap<>();
        map.put(RECIPIENTS_NODE + "/" + currentUser.getId(), new UserRecipient(otherUser.getId(), otherUser.getUsername(), status));
        map.put(SENDERS_NODE + "/" + otherUser.getId(), new UserSender(currentUser.getId(), currentUser.getUsername(), status));
        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(Map.Entry<String, User> entry : map.entrySet()) {

                        dataSnapshot
                            .child(entry.getKey())
                            .getChildren()
                            .iterator()
                            .next()
                            .getRef()
                            .setValue(entry.getValue());
                    }

                    cb.onSuccess();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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

    public static void deleteInvites(User currentUser, User otherUser) {

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
                        }
                    }
                });
    }

    public static void deleteFriend(User currentUser, User otherUser) {

        final HashMap<String, String> paths = new HashMap<>();
        paths.put(currentUser.getId(), otherUser.getUsername());
        paths.put(otherUser.getId(), currentUser.getUsername());
        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(FRIENDS_NODE)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(Map.Entry<String, String> entry : paths.entrySet()) {

                        for(DataSnapshot ds : dataSnapshot.child(entry.getKey()).getChildren()){

                            UserFriend f = ds.getValue(UserFriend.class);
                            if(f.getUsername().equals(entry.getValue())) {

                                ds.getRef().setValue(null);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public static void insertMessages(final List<User> recipients, final Message message) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(MESSAGES_NODE)
            .child(TEXT_MESSAGES_NODE)
            .addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(User user : recipients) {

                        dataSnapshot
                            .child(user.getId())
                            .getRef()
                            .push()
                            .setValue(message);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
