package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.ui.fragments.recipients.FragmentRecipient;
import com.teamtreehouse.ribbit.ui.fragments.recipients.FragmentRecipientsView;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestions;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestionsView;
import com.teamtreehouse.ribbit.utils.Animations;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class MessageActivity extends AppCompatActivity implements MessageActivityView {

    private static final String SUGGESTIONS_TAG = "suggestions";
    private static final String RECIPIENTS_TAG = "recipients";

    @BindView(R.id.root)
    RelativeLayout rootLayout;

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

    protected List<User> recipients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        this.recipients = new LinkedList<>();

        ButterKnife.bind(this);

        progressBar.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(this, R.color.apptheme_color),
                PorterDuff.Mode.SRC_IN
        );

        this.sendImageView.setVisibility(View.INVISIBLE);
        this.messageEditText.setVisibility(View.INVISIBLE);

        recipientsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String input = String.valueOf(charSequence);

                final FragmentSuggestionsView fragment = (FragmentSuggestionsView)
                        getSupportFragmentManager().findFragmentByTag(SUGGESTIONS_TAG);

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
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.recipientsContainer, FragmentRecipient.newInstance(), RECIPIENTS_TAG);
        fragmentTransaction.replace(R.id.suggestionsContainer, FragmentSuggestions.newInstance(), SUGGESTIONS_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void recipientRemoved(int position) {

        this.recipients.remove(position);
    }

    @Override
    public void showSendButton() {

        this.sendImageView.setAnimation(Animations.scaleUp(this));
        this.sendImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSendButton() {

        this.sendImageView.setAnimation(Animations.scaleDown(this));
        this.sendImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showInputEditText() {

        this.messageEditText.setAnimation(Animations.rightTranslate(this));
        this.messageEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInputEditText() {

        this.messageEditText.setAnimation(Animations.leftTranslate(this));
        this.messageEditText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {

        this.sendLayout.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        this.sendLayout.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void itemSelect(Intent intent) {

        User user = intent.getParcelableExtra("user");

        this.recipients.add(user);
        this.recipientsEditText.setText("");

        FragmentRecipientsView fragment = (FragmentRecipientsView) getSupportFragmentManager().findFragmentByTag(RECIPIENTS_TAG);
        fragment.onFriendAdded(user);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootLayout.getWindowToken(), 0);
    }
}
