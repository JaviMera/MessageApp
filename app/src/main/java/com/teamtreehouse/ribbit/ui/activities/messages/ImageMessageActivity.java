package com.teamtreehouse.ribbit.ui.activities.messages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.ImageMessage;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.MultimediaMessage;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.fragments.inbox.messages.FragmentImageMessage;

import java.util.ArrayList;

import butterknife.OnClick;

// TODO add tool bar to view image fragment layout
public class ImageMessageActivity extends MessageActivity {

    private BroadcastReceiver messageBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String data = intent.getStringExtra(Message.KEY);
            Toast.makeText(ImageMessageActivity.this, data, Toast.LENGTH_SHORT).show();

            setSendLayoutVisibility(View.VISIBLE);
            setProgressBarVisibility(View.GONE);
            finish();
        }
    };

    @OnClick(R.id.sendTextImage)
    public void onSendMessageClick(View view) {

        this.presenter.setSendLayoutVisibility(View.GONE);
        this.presenter.setProgressbarVisibility(View.VISIBLE);

        FragmentImageMessage fragment = findFragmentById(R.id.messageContainer);
        Uri pictureUri = fragment.getValue();

        Intent serviceIntent = new Intent(this, ImageMessageService.class);
        serviceIntent.putExtra(ImageMessage.KEY, pictureUri);
        serviceIntent.putParcelableArrayListExtra(User.KEY, new ArrayList<>(this.recipients));
        startService(serviceIntent);
    }
}
