package com.teamtreehouse.ribbit.ui.activities.messages.view;

import java.text.SimpleDateFormat;

/**
 * Created by javie on 4/16/2017.
 */

public interface TimerListener {

    void onTick(String formatedTick);
    void onFinish(String formatedTick);
}
