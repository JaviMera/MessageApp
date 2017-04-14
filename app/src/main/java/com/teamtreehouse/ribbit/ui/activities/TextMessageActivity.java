package com.teamtreehouse.ribbit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.TextMessage;
import com.teamtreehouse.ribbit.ui.fragments.FragmentTextMessage;

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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.messageContainer, FragmentTextMessage.newInstance());
        fragmentTransaction.commit();
    }

    @OnClick(R.id.sendTextImage)
    public void onSendMessageClick(View view) {

        showProgress();

        TextMessage message = new TextMessage(
            UUID.randomUUID().toString(),
            Auth.getInstance().getUsername(),
            messageEditText.getText().toString(),
            new Date().getTime()
        );

        MessageDB.insertMessages(this.recipients, message);
        Toast.makeText(this, "TextMessage sent", Toast.LENGTH_SHORT).show();
        finish();
    }
}
