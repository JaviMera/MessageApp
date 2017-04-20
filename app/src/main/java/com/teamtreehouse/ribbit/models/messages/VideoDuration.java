package com.teamtreehouse.ribbit.models.messages;

import com.teamtreehouse.ribbit.models.messages.MessageDuration;

/**
 * Created by javie on 4/18/2017.
 */

public class VideoDuration extends MessageDuration {

    public VideoDuration(long duration) {

        this.duration = duration;
    }

    @Override
    public long getValue() {

        return duration;
    }
}
