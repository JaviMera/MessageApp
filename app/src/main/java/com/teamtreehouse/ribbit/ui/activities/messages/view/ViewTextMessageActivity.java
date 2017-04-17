package com.teamtreehouse.ribbit.ui.activities.messages.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.TextMessage;
import com.teamtreehouse.ribbit.ui.fragments.inbox.view.ViewTextFragment;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewTextMessageActivity extends ViewMessageActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        TextMessage message = getIntent().getParcelableExtra(Message.KEY);
        getSupportActionBar().setTitle(message.getUsername());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, ViewTextFragment.newInstance(message));
        transaction.commit();
    }
}
