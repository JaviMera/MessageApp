package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;

/**
 * Created by javie on 4/9/2017.
 */

public interface MainActivityView {

    void setPagerAdapter(FragmentManager manager, FragmentPager... fragments);
}
