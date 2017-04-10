package com.teamtreehouse.ribbit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.selectfriends.FragmentSuggestions;

import butterknife.ButterKnife;

/**
 * Created by javie on 4/8/2017.
 */

public class FriendsSelectActivity extends ActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_select);

        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, FragmentSuggestions.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public void itemSelect(final User user) {


    }
}
