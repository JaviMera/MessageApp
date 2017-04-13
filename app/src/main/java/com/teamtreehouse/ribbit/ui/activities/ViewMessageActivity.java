package com.teamtreehouse.ribbit.ui.activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Item;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.TextMessage;
import com.teamtreehouse.ribbit.ui.fragments.ViewMessageFragment;
import com.teamtreehouse.ribbit.ui.fragments.ViewTextFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/11/2017.
 */

public abstract class ViewMessageActivity<TMessage extends Message> extends AppCompatActivity {

    @BindView(R.id.clockTextView)
    TextView clockTextView;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_activity);

        ButterKnife.bind(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TMessage message = getIntent().getParcelableExtra("message");
        Bundle bundle = getIntent().getExtras();
        transaction.replace(R.id.container, getFragment(message, bundle));
        transaction.commit();

        progressBar.setProgress(1000);
        ObjectAnimator anim = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), 0);
        anim.setDuration(10000);
        anim.setInterpolator(new LinearInterpolator());

        CountDownTimer timer = new CountDownTimer(10 * 1000, 900) {

            @Override
            public void onTick(long l) {

                if(progressBar.getProgress() > 999) {

                    clockTextView.setText("00:" + (progressBar.getProgress() / 100));
                }
                else {

                    clockTextView.setText("00:0" + (progressBar.getProgress() / 100));
                }
            }

            @Override
            public void onFinish() {

                clockTextView.setText("00:00");
                ViewMessageFragment fragment = (ViewMessageFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container);

                fragment.onFinish();
            }
        };

        timer.start();
        anim.start();
    }

    protected abstract Fragment getFragment(TMessage message, Bundle bundle);
}
