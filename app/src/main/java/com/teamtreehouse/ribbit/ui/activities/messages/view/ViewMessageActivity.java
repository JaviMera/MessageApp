package com.teamtreehouse.ribbit.ui.activities.messages.view;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.MessageDuration;
import com.teamtreehouse.ribbit.ui.fragments.inbox.view.ViewFragmentMessage;
import com.teamtreehouse.ribbit.ui.fragments.inbox.view.ViewMessageFragmentView;

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
    public static final int TICK_INTERVAL = 1000;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.clockTextView)
    TextView clockTextView;

    @BindView(R.id.timerProgressBar)
    ContentLoadingProgressBar timerProgressBar;

    @BindView(R.id.timerCardView)
    CardView timerCardView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(this, R.color.apptheme_color),
                PorterDuff.Mode.SRC_IN
        );

        timerProgressBar.setProgress(PROGRESS_MAX);
    }

    @Override
    public void onBackPressed() {

        // Cancel the timer
        timer.cancel();

        // Perform the finish code from the timer when the user presses the back button
        timer.onFinish();

        // Navigate back to the parent activity
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected <TFragment extends ViewMessageFragmentView> MessageDuration getMessageDuration() {

        TFragment fragment = (TFragment) getSupportFragmentManager().findFragmentById(R.id.container);

        return fragment.getMessageDuration();
    }

    protected void replaceFragment(int containerId, ViewFragmentMessage fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.commit();
    }

    public void start() {

        // Create a timer object to receive notifications every 1 second of the length of the message
        timer = new MessageTimer(this, getMessageDuration().getValue(), TICK_INTERVAL);

        // Create an animation object to smoothly decreased the progress bar backwards
        ObjectAnimator anim = ObjectAnimator.ofInt(timerProgressBar, "progress", timerProgressBar.getProgress(), 0);
        anim.setDuration(getMessageDuration().getValue());
        anim.setInterpolator(new LinearInterpolator());

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

        ViewMessageFragmentView fragment = (ViewMessageFragmentView) getSupportFragmentManager()
                .findFragmentById(R.id.container);

        fragment.onFinish();
        finish();
    }
}
