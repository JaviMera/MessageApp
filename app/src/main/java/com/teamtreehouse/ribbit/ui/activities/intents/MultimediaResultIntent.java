package com.teamtreehouse.ribbit.ui.activities.intents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.teamtreehouse.ribbit.ui.activities.messages.MessageActivity;

/**
 * Created by javie on 4/18/2017.
 */

public interface MultimediaResultIntent<TActivity extends MessageActivity> {

    Class<TActivity> getActivity();
    Uri getUri(Intent intent, Context context);
}
