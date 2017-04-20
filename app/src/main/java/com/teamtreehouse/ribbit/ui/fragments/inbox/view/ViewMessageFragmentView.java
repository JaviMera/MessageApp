package com.teamtreehouse.ribbit.ui.fragments.inbox.view;

import com.teamtreehouse.ribbit.models.messages.MessageDuration;

/**
 * Created by javie on 4/13/2017.
 */

public interface ViewMessageFragmentView {

    MessageDuration getMessageDuration();
    void onFinish();
}
