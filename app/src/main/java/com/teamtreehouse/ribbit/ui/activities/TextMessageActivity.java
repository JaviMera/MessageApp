package com.teamtreehouse.ribbit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentFriendsSelect;
import com.teamtreehouse.ribbit.ui.fragments.FragmentFriendsSelectView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by javie on 4/9/2017.
 */

public class TextMessageActivity extends ActivityView {

    public static final String FRAGMENT_TAG = "fragment";

    @BindView(R.id.textInput)
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_text);

        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, FragmentFriendsSelect.newInstance(), FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.sendTextButton)
    public void onSendMessageClick(View view) {

        FragmentManager fm = getSupportFragmentManager();
        final FragmentFriendsSelectView fragmentFriendsSelectView =
            (FragmentFriendsSelectView) fm.findFragmentByTag(FRAGMENT_TAG);

        Message message = new Message(
            Auth.getInstance().getUsername(),
            editText.getText().toString(),
            new Date().getTime()
        );

        List<User> recipients = fragmentFriendsSelectView.getRecipients();
        MessageDB.insertMessages(recipients,message);
    }

    @Override
    public void itemSelect(User user) {

    }
}
