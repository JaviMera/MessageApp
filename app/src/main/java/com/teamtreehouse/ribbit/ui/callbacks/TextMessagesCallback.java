package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.messages.TextMessage;

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
