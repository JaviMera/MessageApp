package com.teamtreehouse.ribbit.animations;

import android.view.View;
import android.widget.TextView;

/**
 * Created by javie on 4/20/2017.
 */

public class TextViewAlphaIn implements ViewAnimation {

    private final TextView view;
    private final float value;
    private final int duration;

    public TextViewAlphaIn(TextView view, float value, int duration){

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
        this.view.setVisibility(View.VISIBLE);
    }

    @Override
    public View getView() {
        return this.view;
    }
}
