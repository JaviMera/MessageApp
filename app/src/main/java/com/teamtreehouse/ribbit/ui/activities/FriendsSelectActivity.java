package com.teamtreehouse.ribbit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentFriendsSelect;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerPresenter;
import com.teamtreehouse.ribbit.ui.fragments.search.FragmentSearchAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by javie on 4/8/2017.
 */

public class FriendsSelectActivity extends ActivityView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_select);

        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, FragmentFriendsSelect.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public void itemSelect(final User user) {


    }
}
