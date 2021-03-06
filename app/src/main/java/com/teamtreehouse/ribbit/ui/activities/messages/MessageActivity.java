package com.teamtreehouse.ribbit.ui.activities.messages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.ui.fragments.recipients.FragmentRecipient;
import com.teamtreehouse.ribbit.ui.fragments.recipients.FragmentRecipientsView;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestions;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestionsView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class MessageActivity extends AppCompatActivity implements MessageActivityView, TextWatcher {

    protected abstract int getHint();

    @BindView(R.id.root)
    RelativeLayout rootLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recipientsEditText)
    EditText recipientsEditText;

    @BindView(R.id.textInput)
    EditText messageEditText;

    @BindView(R.id.sendTextImage)
    ImageView sendImageView;

    @BindView(R.id.sendLayout)
    LinearLayout sendLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    HashMap<String, View> views;

    protected MessageActivityPresenter presenter;
    protected List<User> recipients;

    private BroadcastReceiver messageBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String data = intent.getStringExtra(Message.KEY);
            Toast.makeText(MessageActivity.this, data, Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        this.presenter = new MessageActivityPresenter(this);
        this.recipients = new LinkedList<>();

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar.getIndeterminateDrawable().setColorFilter(
            ContextCompat.getColor(this, R.color.apptheme_color),
            PorterDuff.Mode.SRC_IN
        );

        this.presenter.setSendImageVisibility(View.INVISIBLE);
        this.presenter.setMessageEditTextVisibility(View.INVISIBLE);
        this.recipientsEditText.addTextChangedListener(this);

        this.messageEditText.setHint(getHint());

        replaceFragment(R.id.recipientsContainer, FragmentRecipient.newInstance());
        replaceFragment(R.id.suggestionsContainer, FragmentSuggestions.newInstance());
    }


    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(MessageService.MESSAGE_ACTION_VIEW);
        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(this.messageBroadcast, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager
            .getInstance(this)
            .unregisterReceiver(this.messageBroadcast);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void replaceFragment(int containerId, Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public <TFragment> TFragment findFragmentById(int containerId) {

        return (TFragment) getSupportFragmentManager().findFragmentById(containerId);
    }

    @Override
    public void recipientRemoved(int position) {

        this.recipients.remove(position);
    }

    @Override
    public void itemSelect(Intent intent) {

        User user = intent.getParcelableExtra("user");

        this.recipients.add(user);
        this.recipientsEditText.setText("");

        FragmentRecipientsView fragment = findFragmentById(R.id.recipientsContainer);
        fragment.onFriendAdded(user);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootLayout.getWindowToken(), 0);
    }

    @Override
    public void setSendImageVisibility(int visibility) {

        this.sendImageView.setVisibility(visibility);
    }

    @Override
    public void setMessageEditTextVisibility(int visible) {

        this.messageEditText.setVisibility(visible);
    }

    @Override
    public void setSendLayoutVisibility(int visibility) {

        this.sendLayout.setVisibility(visibility);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {

        this.progressBar.setVisibility(visibility);
    }

    @Override
    public void setSendImageViewAnimation(Animation anim) {

        this.sendImageView.setAnimation(anim);
    }

    @Override
    public void setMessageEditTextAnimation(Animation anim) {

        this.messageEditText.setAnimation(anim);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        String input = String
            .valueOf(charSequence)
            .toLowerCase()
            .trim();

        final FragmentSuggestionsView fragment = findFragmentById(R.id.suggestionsContainer);

        if (input.isEmpty()) {

            // Pass in an empty list of users, when an empty string is typed in the search view
            fragment.setItems(new LinkedList<User>());
            return;
        }

        MessageDB.filterFriends(Auth.getInstance().getId(), input, new UserReadCallback() {

            @Override
            public void onUserRead(List<User> user) {

                // After getting the filtered friends, check if any of the results contains
                // recipients that the user may have already added.
                for(User recipient : recipients) {

                    if(user.contains(recipient)) {

                        // If the new set of results contains a recipient, then remove it
                        // as we don't want to give the option to add the same user twice.
                        user.remove(recipient);
                    }
                }

                fragment.setItems(user);
            }
        });
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
