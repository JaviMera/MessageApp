package com.teamtreehouse.ribbit.animations;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by javie on 4/20/2017.
 */

public class FabDown implements ViewAnimation {

    private final FloatingActionButton view;
    private final float value;
    private final int duration;

    public FabDown(FloatingActionButton view, float value, int duration){

        this.view = view;
        this.value = value;
        this.duration = duration;
    }

    @Override
    public float getValue() {

        return this.value;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public void endAction() {

        this.view.hide();
    }

    @Override
    public View getView() {

        return this.view;
    }
}
