package com.teamtreehouse.ribbit.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.TextMessage;
import com.teamtreehouse.ribbit.ui.activities.ViewMessageActivity;
import com.teamtreehouse.ribbit.ui.fragments.ViewTextFragment;

import butterknife.BindView;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewTextMessageActivity extends ViewMessageActivity<TextMessage> {

    @Override
    protected Fragment getFragment(TextMessage message, Bundle bundle) {

        return ViewTextFragment.newInstance(message);
    }
}
