package com.teamtreehouse.ribbit.ui.activities.messages.view;

import android.os.CountDownTimer;

import java.text.SimpleDateFormat;

/**
 * Created by javie on 4/16/2017.
 */

public class MessageTimer extends CountDownTimer {

    private TimerListener listener;

    public MessageTimer(TimerListener listener, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

        this.listener = listener;
    }

    @Override
    public void onTick(long l) {

        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        this.listener.onTick(formatter.format(l));
    }

    @Override
    public void onFinish() {

        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        this.listener.onFinish(formatter.format(0L));
    }
}
