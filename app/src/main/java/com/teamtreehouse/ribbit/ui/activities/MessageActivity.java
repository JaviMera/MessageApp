package com.teamtreehouse.ribbit.ui.activities;

import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 4/9/2017.
 */
public abstract class MessageActivity extends ActivityBase {

    public abstract void recipientRemoved(int position);
    public abstract void showSendButton();
    public abstract void hideSendButton();
}
