package com.teamtreehouse.ribbit.ui.activities.messages;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

import com.teamtreehouse.ribbit.database.MultimediaInsertCallback;
import com.teamtreehouse.ribbit.database.MultimediaStorageCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.MessageStorage;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.ImageMessage;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.VideoMessage;
import com.teamtreehouse.ribbit.models.users.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by javie on 4/21/2017.
 */

public class ImageMessageService extends MessageService {

    @Override
    protected void handle(Intent intent) {

        Uri pictureUri = intent.getParcelableExtra(VideoMessage.KEY);
        final ArrayList<User> recipients = intent.getParcelableArrayListExtra(User.KEY);

        for (final User user : recipients) {

            MessageStorage.insertPicture(user.getId(), pictureUri, new MultimediaStorageCallback() {
                @Override
                public void onSuccess(String url, String path) {

                    ImageMessage message = new ImageMessage(
                        UUID.randomUUID().toString(),
                        Auth.getInstance().getUsername(),
                        url,
                        path,
                        new Date().getTime()
                    );

                    MessageDB.insertImageMessage(user.getId(), message, new MultimediaInsertCallback() {

                        @Override
                        public void onSuccess() {

                            // Check if its the last message that was sent successfully
                            if(recipients.indexOf(user) == recipients.size() - 1) {

                                // Notify main activity about all messages sent
                                Intent responseIntent = new Intent(MESSAGE_ACTION_VIEW);
                                responseIntent.putExtra(Message.KEY, "Message Sent!");
                                LocalBroadcastManager
                                    .getInstance(ImageMessageService.this).sendBroadcast(responseIntent);
                            }
                        }
                    });
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }
}
