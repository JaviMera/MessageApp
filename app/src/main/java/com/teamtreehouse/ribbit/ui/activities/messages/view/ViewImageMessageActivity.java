package com.teamtreehouse.ribbit.ui.activities.messages.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.models.Message;
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

        Bundle bundle = getIntent().getExtras();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, ViewImageMessageFragment.newInstance(message, bundle));
        transaction.commit();
    }
}
