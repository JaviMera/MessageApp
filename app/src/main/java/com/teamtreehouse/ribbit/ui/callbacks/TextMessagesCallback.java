package com.teamtreehouse.ribbit.ui.callbacks;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.TextMessage;

/**
 * Created by javie on 4/9/2017.
 */

public class TextMessagesCallback extends MessageCallback<TextMessage> {

    public TextMessagesCallback(String userId, MessageListener<TextMessage> listener) {

        super(userId, listener);

    }

    @Override
    protected Class<TextMessage> getClassType() {

        return TextMessage.class;
    }

    @Override
    protected String getMessageNode() {

        return "text";
    }
}
