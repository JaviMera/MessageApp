package com.teamtreehouse.ribbit.ui.activities.messages.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.TextMessage;
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

        replaceFragment(R.id.container, ViewTextFragment.newInstance(message));
    }
}
