package com.teamtreehouse.ribbit.ui.activities.messages.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.ImageMessage;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.ui.fragments.inbox.view.ViewImageMessageFragment;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewImageMessageActivity extends ViewMessageActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ImageMessage message = getIntent().getParcelableExtra(Message.KEY);
        getSupportActionBar().setTitle(message.getUsername());

        replaceFragment(R.id.container, ViewImageMessageFragment.newInstance(message));
    }
}
