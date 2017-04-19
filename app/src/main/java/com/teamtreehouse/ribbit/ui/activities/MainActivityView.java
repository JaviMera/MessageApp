package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;

/**
 * Created by javie on 4/9/2017.
 */

public interface MainActivityView extends ActivityView{

    void showFabMenu();
    void hideFabMenu();
    int getFabMenuVisibility();
    boolean checkPermissions(String[] permissionsStorage);
    void requestPermissions(String[] permissionsStorage);
}
