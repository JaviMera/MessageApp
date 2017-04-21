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

public class TextMessageService extends MessageService<TextMessage>{

    @Override
    protected void handle(TextMessage message, final ArrayList<User> recipients) {

        final int notificationId = 001;
        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
            .setContentTitle("Message upload")
            .setContentText("Download in progress")
            .setSmallIcon(android.R.drawable.stat_sys_upload)
            .setOngoing(true);

        for(final User user : recipients) {

            MessageDB.insertTextMessage(user.getId(), message, new TextInsertCallback() {
                @Override
                public void onSuccess() {

                    builder.setProgress(PROGRESS_MAX, PROGRESS_MAX / recipients.size(), false);
                    manager.notify(notificationId, builder.build());
                }

                @Override
                public void onFailure() {
                }
            });
        }

        try {

            // Because text messages will probably not take any time to be sent
            // Make the service sleep 2 seconds before publishing the done upload icon
            Thread.sleep(2000);

            // After the message sending is done, send a last notification showing the user
            // everything was sent successfully
            builder
                    .setContentText("Download complete")
                    .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                    .setProgress(0,0, false)
                    .setOngoing(false)
                    .setAutoCancel(true);

            manager.notify(notificationId, builder.build());

            // Notify main activity about messages successfully sent
            Intent responseIntent = new Intent("message_send");
            responseIntent.putExtra(Message.KEY, "success");
            LocalBroadcastManager
                    .getInstance(TextMessageService.this)
                    .sendBroadcast(responseIntent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
