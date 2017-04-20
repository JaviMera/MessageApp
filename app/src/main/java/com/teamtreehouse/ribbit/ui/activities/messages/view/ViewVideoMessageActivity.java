package com.teamtreehouse.ribbit.ui.activities.messages.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.VideoMessage;
import com.teamtreehouse.ribbit.ui.fragments.inbox.view.ViewVideoMessageFragment;

/**
 * Created by javie on 4/17/2017.
 */

public class ViewVideoMessageActivity extends ViewMessageActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        VideoMessage message = getIntent().getParcelableExtra(Message.KEY);
        getSupportActionBar().setTitle(message.getUsername());

        replaceFragment(R.id.container, ViewVideoMessageFragment.newInstance(message));
    }
}
