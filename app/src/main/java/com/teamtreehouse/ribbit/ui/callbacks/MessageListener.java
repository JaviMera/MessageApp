package com.teamtreehouse.ribbit.ui.callbacks;

import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.models.Message;

/**
 * Created by javie on 4/19/2017.
 */

public interface MessageListener<TMessage extends Message> {

    void onMessageAdded(TMessage message);
    void onMessageRemoved(TMessage message);
}
