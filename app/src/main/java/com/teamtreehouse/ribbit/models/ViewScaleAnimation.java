package com.teamtreehouse.ribbit.models;

import android.view.View;

/**
 * Created by javie on 4/20/2017.
 */

public interface ViewScaleAnimation {

    float getValue();
    int getDuration();
    void endAction();
    View getView();
}
