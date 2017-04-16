package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;

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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, ViewTextFragment.newInstance(message));
        transaction.commit();
    }
}
