package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.Message;

/**
 * Created by javie on 4/11/2017.
 */

public interface MessageRecipient {

    void onMessageAdded(Message msg);
    void onMessageRemoved(Message message);
}