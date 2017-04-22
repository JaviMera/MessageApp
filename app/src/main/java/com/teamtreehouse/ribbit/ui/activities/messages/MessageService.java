package com.teamtreehouse.ribbit.ui.activities.messages;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.MessageStorage;
import com.teamtreehouse.ribbit.database.TextInsertCallback;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.TextMessage;
import com.teamtreehouse.ribbit.models.users.User;

import java.util.ArrayList;

/**
 * Created by javie on 4/21/2017.
 */

public abstract class MessageService extends IntentService {

    protected abstract void handle(Intent intent);

    public MessageService(){

        super("MessageService");
    }

    public MessageService(String name) {

        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        handle(intent);
    }
}
