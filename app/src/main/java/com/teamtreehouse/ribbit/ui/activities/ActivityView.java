package com.teamtreehouse.ribbit.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 4/7/2017.
 */

public abstract class ActivityView extends AppCompatActivity{

    public void editFriend(User user) {

        Intent intent = new Intent(ActivityView.this, EditFriendActivity.class);
        intent.putExtra(EditFriendActivity.EDIT_FRIEND_KEY, user);
        startActivity(intent);
    }
}
