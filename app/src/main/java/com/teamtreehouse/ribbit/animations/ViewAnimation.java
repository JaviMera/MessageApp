package com.teamtreehouse.ribbit.animations;

import android.view.View;

/**
 * Created by javie on 4/20/2017.
 */

public interface ViewAnimation {

    float getValue();
    int getDuration();
    void endAction();
    View getView();
}
