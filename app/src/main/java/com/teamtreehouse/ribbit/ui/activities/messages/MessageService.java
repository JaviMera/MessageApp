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

public class MessageService extends IntentService {

    public MessageService(){

        super("MessageService");
    }

    public MessageService(String name) {

        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        TextMessage message = intent.getParcelableExtra(Message.KEY);
        final ArrayList<User> recipients = intent.getParcelableArrayListExtra(User.KEY);

        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Message upload");
        builder.setContentText("Download in progress");
        builder.setSmallIcon(android.R.drawable.stat_sys_upload);
        builder.setOngoing(true);
        final int notificationId = 001;
        try {

            for(final User user : recipients) {

                MessageDB.insertTextMessage(user.getId(), message, new TextInsertCallback() {
                    @Override
                    public void onSuccess() {

                        builder.setProgress(100, 100 / recipients.size(), false);
                        manager.notify(notificationId, builder.build());
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }

            Thread.sleep(2000);

            builder
                .setContentText("Download complete")
                .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                .setProgress(0,0, false)
                .setOngoing(false)
                .setAutoCancel(true);

            manager.notify(notificationId, builder.build());

            Intent responseIntent = new Intent("message_send");
            responseIntent.putExtra(Message.KEY, "success");
            LocalBroadcastManager
                .getInstance(MessageService.this)
                .sendBroadcast(responseIntent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
