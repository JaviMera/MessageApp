package com.teamtreehouse.ribbit.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.LoginActivity;
import com.teamtreehouse.ribbit.ui.SignUpActivity;
import com.teamtreehouse.ribbit.ui.callbacks.InsertCallback;

/**
 * Created by javie on 4/4/2017.
 */

public class MessageDB {

    public static final String USERS_NODE = "users";

    public static void insertUser(final User newUser, final InsertCallback listener) {

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
}
