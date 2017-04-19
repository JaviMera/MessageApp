package com.teamtreehouse.ribbit.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

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

    public static Animation scale(Context ctx, int anim) {

        return AnimationUtils.loadAnimation(ctx, anim);
    }

    public static void scaleDownFab(final FloatingActionButton fab, float value, int duration){

        ViewCompat
            .animate(fab)
            .scaleX(value)
            .scaleY(value)
            .setDuration(duration)
            .setInterpolator(new LinearInterpolator())
            .setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {

                    fab.hide();
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            })
            .start();
    }

    public static void scaleUpFab(final FloatingActionButton fab, float value, int duration){

        ViewCompat
            .animate(fab)
            .scaleX(value)
            .scaleY(value)
            .setDuration(duration)
            .setInterpolator(new LinearInterpolator())
            .setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {

                    fab.show();
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            })
            .start();
    }
}
