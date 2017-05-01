package com.teamtreehouse.ribbit.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.ui.activities.LoginActivity;
import com.teamtreehouse.ribbit.ui.activities.MainActivity;
import com.teamtreehouse.ribbit.ui.callbacks.ImageMessagesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.MessageListener;
import com.teamtreehouse.ribbit.ui.callbacks.TextMessagesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.VideoMessagesCallback;

/**
 * Created by javie on 5/1/2017.
 */

public class InboxService extends Service implements MessageListener {

    private IBinder binder;
    private TextMessagesCallback messagesCallback;
    private ImageMessagesCallback imageMessagesCallback;
    private VideoMessagesCallback videoMessagesCallback;
    private NotificationManager manager;
    private boolean withPendingIntent;

    @Override
    public void onCreate() {

        this.binder = new InboxBinder();

        String userId = Auth.getInstance().getId();
        this.messagesCallback = new TextMessagesCallback(userId, this);
        this.imageMessagesCallback = new ImageMessagesCallback(userId, this);
        this.videoMessagesCallback = new VideoMessagesCallback(userId, this);
        manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        withPendingIntent = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return this.binder;
    }

    @Override
    public void onMessageAdded(Message message) {

        Intent intent = null;
        PendingIntent pendingIntent = null;
        boolean vibrate = false;
        if(this.withPendingIntent) {

            intent = new Intent(this, LoginActivity.class);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            vibrate = true;
        }

        Notification.Builder notification = new Notification.Builder(this)
            .setContentTitle("Ribbit")
            .setContentText("You have new Messages!")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent);

        if(vibrate) {

            notification.setDefaults(Notification.DEFAULT_VIBRATE);
            notification.setPriority(Notification.PRIORITY_HIGH);
        }

        this.manager.notify(0, notification.build());
    }

    @Override
    public void onMessageRemoved(Message message) {

    }

    public void setWithPendingIntent(boolean withPendingIntent) {

        this.withPendingIntent = withPendingIntent;
    }

    public class InboxBinder extends Binder {

        public InboxService getService() {

            return InboxService.this;
        }
    }
}
