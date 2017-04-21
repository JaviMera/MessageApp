package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;

/**
 * Created by javie on 4/9/2017.
 */

public class MainActivityPresenter {

    private MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {

        this.view = view;
    }

    public void showFabMenu() {

        this.view.showFabMenu();
    }

    public void hideFabMenu() {

        this.view.hideFabMenu();
    }

    public int getFabMenuVisitibility() {

        return this.view.getFabMenuVisibility();
    }

    public boolean checkPermissions(String[] permissions) {

        return this.view.checkPermissions(permissions);
    }

    public void requestPermissions(String[] permissions) {

        this.view.requestPermissions(permissions);
    }

    public void setFabIcon(int icon) {

        this.view.setFabIcon(icon);
    }
}
