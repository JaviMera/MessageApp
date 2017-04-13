package com.teamtreehouse.ribbit.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.ui.fragments.ViewImageMessageFragment;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewImageMessageActivity extends ViewMessageActivity<ImageMessage> {

    @Override
    protected Fragment getFragment(ImageMessage message, Bundle bundle) {

        return ViewImageMessageFragment.newInstance(message, bundle);
    }
}
