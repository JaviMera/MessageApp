package com.teamtreehouse.ribbit.database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.ImageMessage;
import com.teamtreehouse.ribbit.models.messages.MultimediaMessage;
import com.teamtreehouse.ribbit.models.messages.TextMessage;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserCurrent;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.models.users.UserRecipient;
import com.teamtreehouse.ribbit.models.users.UserSender;
import com.teamtreehouse.ribbit.models.users.UserRequest;
import com.teamtreehouse.ribbit.models.messages.VideoMessage;
import com.teamtreehouse.ribbit.ui.callbacks.InviteDeleteCallback;

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
    public static final String IMAGES_NODE = "images";
    public static final String MULTIMEDIA_PATH_PROP = "path";
    public static final String TEXT_MESSAGE_ID_PROPERTY = "id";
    private static final String VIDEOS_NODE = "videos";

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
            .setValue(new UserRecipient(currentUser.getId(), currentUser.getEmail(), currentUser.getUsername(), currentUser.getPhotoUrl()));

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(INVITES_NODE)
            .child(SENDERS_NODE)
            .child(currentUser.getId())
            .push()
            .setValue(new UserSender(otherUser.getId(), otherUser.getEmail(), otherUser.getUsername(), otherUser.getPhotoUrl()));
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

    public static void filterFriends(String currentUserId, String username, final UserReadCallback filterUserCallback){

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(FRIENDS_NODE)
            .child(currentUserId)
            .orderByChild("username")
            .startAt(username)
            .endAt(username+"\uf8ff")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<User> users = new LinkedList<User>();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {

                        users.add(ds.getValue(UserFriend.class));
                    }

                    filterUserCallback.onUserRead(users);
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

    public static void insertFriends(User currentUser, User otherUser) {

        FirebaseDatabase.getInstance().getReference()
            .child(FRIENDS_NODE)
            .child(currentUser.getId())
            .push()
            .setValue(new UserFriend(otherUser.getId(), otherUser.getUsername()));

        FirebaseDatabase.getInstance().getReference()
            .child(FRIENDS_NODE)
            .child(otherUser.getId())
            .push()
            .setValue(new UserFriend(currentUser.getId(), currentUser.getUsername()));
    }

    public static void deleteInvites(User currentUser, User otherUser, final InviteDeleteCallback callback) {

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

                            callback.onInviteDeleted();
                        }
                    }
                });
    }

    public static void deleteFriend(User currentUser, User otherUser, final DeleteFriendCallback callback) {

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

                    callback.onDeleteFriend();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public static void insertTextMessage(final String recipientId, TextMessage message, final TextInsertCallback callback) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(MESSAGES_NODE)
            .child(TEXT_MESSAGES_NODE)
            .child(recipientId)
            .push()
            .setValue(message)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    callback.onSuccess();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    callback.onFailure();
                }
            });
    }

    public static void insertImageMessage(String recipientId, ImageMessage message, final MultimediaInsertCallback callback) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(MESSAGES_NODE)
            .child(IMAGES_NODE)
            .child(recipientId)
            .push()
            .setValue(message)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    callback.onSuccess();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
    }

    public static void insertVideoMessage(String recipientId, ImageMessage message, final MultimediaInsertCallback callback) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(MESSAGES_NODE)
            .child(VIDEOS_NODE)
            .child(recipientId)
            .push()
            .setValue(message)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    callback.onSuccess();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
    }

    public static void deleteTextMessage(String userId, final String messageId, final DeleteTextCallback callback) {

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child(MESSAGES_NODE)
                .child(TEXT_MESSAGES_NODE)
                .child(userId)
                .runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {

                        for(MutableData md : mutableData.getChildren()) {

                            HashMap<String, String> map = (HashMap<String, String>) md.getValue();

                            if(map.get(TEXT_MESSAGE_ID_PROPERTY).equals(messageId)) {

                                md.setValue(null);
                                return Transaction.success(mutableData);
                            }
                        }

                        return Transaction.abort();
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                        if(databaseError == null) {

                            callback.onSuccess();
                        }
                        else {

                            callback.onFailure(databaseError.getMessage());
                        }

                    }
                });
    }

    public static void readMessages(String userId, String messageNode, ChildEventListener listener) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(MESSAGES_NODE)
            .child(messageNode)
            .child(userId)
            .addChildEventListener(listener);
    }

    public static void deleteImageMessage(String userId, final ImageMessage message, final DeleteMultimediaFileCallback callback) {

        deleteMultimediaMessage(userId, IMAGES_NODE, message, callback);
    }

    public static void deleteVideoMessage(String userId, final VideoMessage message, final DeleteMultimediaFileCallback callback) {

        deleteMultimediaMessage(userId, VIDEOS_NODE, message, callback);
    }

    private static void deleteMultimediaMessage( String userId, String node, final MultimediaMessage message, final DeleteMultimediaFileCallback callback) {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child(MESSAGES_NODE)
            .child(node)
            .child(userId)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        HashMap<String, String> currentMessage = (HashMap<String, String>) ds.getValue();
                        if (currentMessage.get(MULTIMEDIA_PATH_PROP).equals(message.getPath())) {

                            ds.getRef().setValue(null);
                            break;
                        }
                    }

                    callback.onSuccess();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    callback.onFailure(databaseError.getMessage());
                }
            });
    }
}
