package com.teamtreehouse.ribbit.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 4/7/2017.
 */

public abstract class ActivityBase extends AppCompatActivity{

    public abstract void itemSelect(Intent intent);
}
