package com.teamtreehouse.ribbit.ui.activities.messages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.TextInsertCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.TextMessage;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.TextMessageService;
import com.teamtreehouse.ribbit.ui.fragments.inbox.messages.FragmentTextMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import butterknife.OnClick;

/**
 * Created by javie on 4/14/2017.
 */

public class TextMessageActivity extends MessageActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.messageContainer, FragmentTextMessage.newInstance());
    }

    @OnClick(R.id.sendTextImage)
    public void onSendMessageClick(View view) {

        this.presenter.setSendLayoutVisibility(View.GONE);
        this.presenter.setProgressbarVisibility(View.VISIBLE);

        TextMessage message = new TextMessage(
            UUID.randomUUID().toString(),
            Auth.getInstance().getUsername(),
            messageEditText.getText().toString(),
            new Date().getTime()
        );

        Intent serviceIntent = new Intent(this, TextMessageService.class);
        serviceIntent.putParcelableArrayListExtra(User.KEY, new ArrayList<>(this.recipients));
        serviceIntent.putExtra(Message.KEY, message);
        startService(serviceIntent);
    }
}
