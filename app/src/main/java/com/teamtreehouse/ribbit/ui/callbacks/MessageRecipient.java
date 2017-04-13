package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.TextMessage;

/**
 * Created by javie on 4/11/2017.
 */

public interface MessageRecipient {

    void onMessageAdded(TextMessage msg);
    void onMessageRemoved(TextMessage message);
}