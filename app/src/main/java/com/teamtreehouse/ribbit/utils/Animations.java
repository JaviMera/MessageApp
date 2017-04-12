package com.teamtreehouse.ribbit.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.teamtreehouse.ribbit.R;

/**
 * Created by javie on 4/12/2017.
 */

public class Animations {

    public static Animation rightTranslate(Context ctx) {

        return AnimationUtils.loadAnimation(ctx, R.anim.slide_right);
    }

    public static Animation leftTranslate(Context ctx) {

        return AnimationUtils.loadAnimation(ctx, R.anim.slide_left);
    }

    public static Animation scaleUp(Context ctx) {

        return AnimationUtils.loadAnimation(ctx, R.anim.scale_up);
    }

    public static Animation scaleDown(Context ctx) {

        return AnimationUtils.loadAnimation(ctx, R.anim.scale_down);
    }
}
