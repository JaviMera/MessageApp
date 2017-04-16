package com.teamtreehouse.ribbit.ui.activities;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.ui.activities.messages.view.MessageTimer;
import com.teamtreehouse.ribbit.ui.activities.messages.view.TimerListener;
import com.teamtreehouse.ribbit.ui.fragments.inbox.view.ViewMessageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/11/2017.
 */

public abstract class ViewMessageActivity
    extends
        AppCompatActivity
    implements ViewMessageActivityView, TimerListener {

    public static final int PROGRESS_MAX = 1000;
    public static final int PROGRESS_ANIM_DURATION = 10000;
    public static final int TICK_INTERVAL = 1000;
    @BindView(R.id.clockTextView)
    TextView clockTextView;

    @BindView(R.id.timerProgressBar)
    ContentLoadingProgressBar timerProgressBar;

    @BindView(R.id.timerCardView)
    CardView timerCardView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private CountDownTimer timer;
    private ObjectAnimator anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_activity);

        ButterKnife.bind(this);

        progressBar.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(this, R.color.apptheme_color),
                PorterDuff.Mode.SRC_IN
        );

        timerProgressBar.setProgress(PROGRESS_MAX);
        anim = ObjectAnimator.ofInt(timerProgressBar, "progress", timerProgressBar.getProgress(), 0);
        anim.setDuration(PROGRESS_ANIM_DURATION);
        anim.setInterpolator(new LinearInterpolator());

        timer = new MessageTimer(this, PROGRESS_ANIM_DURATION, TICK_INTERVAL);
    }

    @Override
    public void onBackPressed() {

        // Cancel the timer
        timer.cancel();

        // Perform the finish code from the timer when the user presses the back button
        timer.onFinish();
        super.onBackPressed();
    }

    public void start() {

        anim.start();
        timer.start();
    }

    @Override
    public void setProgressbarVisibility(int visibility) {

        this.progressBar.setVisibility(visibility);
    }

    @Override
    public void setCardViewVisibility(int viewVisibility) {

        this.timerCardView.setVisibility(viewVisibility);
    }

    @Override
    public void onTick(String formatedTick) {

        this.clockTextView.setText(formatedTick);
    }

    @Override
    public void onFinish(String formatedTick) {

        this.clockTextView.setText(formatedTick);

        ViewMessageFragment fragment = (ViewMessageFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);

        fragment.onFinish();
        finish();
    }
}
