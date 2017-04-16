package com.teamtreehouse.ribbit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.TextInsertCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.TextMessage;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.inbox.messages.FragmentTextMessage;

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

        for(final User user : this.recipients) {

            MessageDB.insertTextMessage(user.getId(), message, new TextInsertCallback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure() {

                    Toast
                        .makeText(
                            TextMessageActivity.this,
                            "Unable to send message to " + user.getUsername(),
                            Toast.LENGTH_SHORT)
                        .show();
                }
            });
        }

        finish();
    }
}
