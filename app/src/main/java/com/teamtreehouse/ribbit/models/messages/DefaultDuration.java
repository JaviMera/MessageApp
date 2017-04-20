package com.teamtreehouse.ribbit.models.messages;

import com.teamtreehouse.ribbit.models.messages.MessageDuration;

/**
 * Created by javie on 4/18/2017.
 */

public class DefaultDuration extends MessageDuration {

    @Override
    public long getValue() {

        return 5000L;
    }
}
