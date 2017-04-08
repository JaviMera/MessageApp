package com.teamtreehouse.ribbit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.ui.activities.MainActivity;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.fragments.friends.FriendsFragment;
import com.teamtreehouse.ribbit.ui.fragments.messages.InboxFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected MainActivity activity;
    private Fragment[] fragments;

    public SectionsPagerAdapter(MainActivity context, FragmentManager fm, Fragment... fragments) {
        super(fm);
        activity = context;

        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return this.fragments[position];
    }

    @Override
    public int getCount() {
        return this.fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "";
    }

    public int getIcon(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_tab_inbox;
            case 1:
                return R.drawable.ic_tab_friends;
        }

        return R.drawable.ic_tab_inbox;
    }
}





