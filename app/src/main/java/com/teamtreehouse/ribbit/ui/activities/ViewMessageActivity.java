package com.teamtreehouse.ribbit.ui.activities;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.ui.fragments.ViewMessageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/11/2017.
 */

public abstract class ViewMessageActivity<TMessage extends Message> extends AppCompatActivity {

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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TMessage message = getIntent().getParcelableExtra("message");
        Bundle bundle = getIntent().getExtras();
        transaction.replace(R.id.container, getFragment(message, bundle));
        transaction.commit();

        timerProgressBar.setProgress(1000);
        anim = ObjectAnimator.ofInt(timerProgressBar, "progress", timerProgressBar.getProgress(), 0);
        anim.setDuration(10000);
        anim.setInterpolator(new LinearInterpolator());

        timer = createTimer(anim);
    }

    private CountDownTimer createTimer(final ObjectAnimator anim) {

        return new CountDownTimer(10 * 1000, 900) {

            @Override
            public void onTick(long l) {

                if(!anim.isStarted()) {

                    anim.start();
                }

                if(timerProgressBar.getProgress() > 999) {

                    clockTextView.setText("00:" + (timerProgressBar.getProgress() / 100));
                }
                else {

                    clockTextView.setText("00:0" + (timerProgressBar.getProgress() / 100));
                }
            }

            @Override
            public void onFinish() {

                clockTextView.setText("00:00");
                ViewMessageFragment fragment = (ViewMessageFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.container);

                fragment.onFinish();
                exit();
            }
        };
    }

    @Override
    public void onBackPressed() {

        ViewMessageFragment fragment = (ViewMessageFragment) getSupportFragmentManager()
            .findFragmentById(R.id.container);

        fragment.onFinish();

        super.onBackPressed();
    }

    public void start() {

        timer.start();
    }

    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
        timerCardView.setVisibility(View.GONE);
    }

    public void hideProgress() {

        progressBar.setVisibility(View.GONE);
        timerCardView.setVisibility(View.VISIBLE);
    }

    protected abstract Fragment getFragment(TMessage message, Bundle bundle);

    public void exit() {

        finish();
    }
}
