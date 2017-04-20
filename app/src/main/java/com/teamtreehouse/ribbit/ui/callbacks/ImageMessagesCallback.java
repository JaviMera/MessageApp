package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.messages.ImageMessage;

/**
 * Created by javie on 4/13/2017.
 */

public class ImageMessagesCallback extends MessageCallback<ImageMessage>{

    private MessageListener<ImageMessage> listener;

    public ImageMessagesCallback(String userId, MessageListener<ImageMessage> listener) {

        super(userId, listener);
    }

    @Override
    protected String getMessageNode() {

        return "images";
    }

    @Override
    protected Class<ImageMessage> getClassType() {

        return ImageMessage.class;
    }
}
