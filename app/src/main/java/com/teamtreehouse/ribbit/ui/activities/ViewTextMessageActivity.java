package com.teamtreehouse.ribbit.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.Message;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/11/2017.
 */

public class ViewTextMessageActivity extends AppCompatActivity {

    @BindView(R.id.messageTextView)
    TextView messageTextView;

    @BindView(R.id.clockTextView)
    TextView clockTextView;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    private Message friendMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_text_activity);

        ButterKnife.bind(this);

        friendMessage = getIntent().getParcelableExtra("message");
        messageTextView.setText(friendMessage.getText());
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

                FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("messages")
                    .child("text")
                    .child(Auth.getInstance().getId())
                    .runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {

                            for(MutableData md : mutableData.getChildren()) {

                                HashMap<String, String> map = (HashMap<String, String>) md.getValue();

                                if(map.get("id").equals(friendMessage.getId())) {

                                    md.setValue(null);
                                    return Transaction.success(mutableData);
                                }
                            }

                            return null;
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                            if(databaseError == null) {

                                Intent intent = new Intent();
                                intent.putExtra("message", friendMessage);
                                setResult(1000, intent);
                                finish();
                            }

                        }
                    }
                );
            }
        };

        timer.start();
        anim.start();
    }
}
