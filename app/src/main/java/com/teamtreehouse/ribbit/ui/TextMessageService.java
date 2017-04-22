package com.teamtreehouse.ribbit.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.TextInsertCallback;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.TextMessage;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.activities.messages.MessageService;

import java.util.ArrayList;

/**
 * Created by javie on 4/21/2017.
 */

public class TextMessageService extends MessageService{

    @Override
    protected void handle(Intent intent) {

        TextMessage message = intent.getParcelableExtra(Message.KEY);
        final ArrayList<User> recipients = intent.getParcelableArrayListExtra(User.KEY);

        for(final User user : recipients) {

            MessageDB.insertTextMessage(user.getId(), message, new TextInsertCallback() {
                @Override
                public void onSuccess() {

                    if(recipients.indexOf(user) == recipients.size() - 1) {

                        // Notify main activity about messages successfully sent
                        Intent responseIntent = new Intent("message_send");
                        responseIntent.putExtra(Message.KEY, "success");
                        LocalBroadcastManager
                                .getInstance(TextMessageService.this)
                                .sendBroadcast(responseIntent);
                    }
                }

                @Override
                public void onFailure() {
                }
            });
        }
    }
}
