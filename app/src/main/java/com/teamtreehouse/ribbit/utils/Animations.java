package com.teamtreehouse.ribbit.utils;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.animations.ViewAnimationCallback;
import com.teamtreehouse.ribbit.animations.ViewAnimation;

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

    public static void alpha(final ViewAnimation animation) {

        ViewCompat
            .animate(animation.getView())
            .alpha(animation.getValue())
            .setDuration(animation.getDuration())
            .setInterpolator(new FastOutLinearInInterpolator())
            .setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {

                    animation.endAction();
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            }).start();
    }

    public static void scale(final ViewAnimation animation, final ViewAnimationCallback callback){

        ViewCompat
            .animate(animation.getView())
            .scaleX(animation.getValue())
            .scaleY(animation.getValue())
            .setDuration(animation.getDuration())
            .setInterpolator(new LinearInterpolator())
            .setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {

                    animation.endAction();

                    if(callback == null)
                        return;

                    callback.onFinish();
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            })
            .start();
    }
}
