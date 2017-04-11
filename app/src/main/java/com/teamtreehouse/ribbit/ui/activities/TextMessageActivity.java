package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.callbacks.UserReadCallback;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecipient;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecipientsView;
import com.teamtreehouse.ribbit.ui.fragments.selectfriends.FragmentSuggestions;
import com.teamtreehouse.ribbit.ui.fragments.selectfriends.FragmentSuggestionsView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextMessageActivity extends MessageActivity {

    public static final String SUGGESTIONS_TAG = "suggestions";
    private static final String RECIPIENTS_TAG = "recipients";

    @BindView(R.id.root)
    RelativeLayout rootLayout;

    @BindView(R.id.recipientsEditText)
    EditText recipientsEditText;

    @BindView(R.id.textInput)
    EditText messageEditText;

    private List<User> recipients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_text);

        this.recipients = new LinkedList<>();

        ButterKnife.bind(this);

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
        fragmentTransaction.replace(R.id.container, FragmentSuggestions.newInstance(), SUGGESTIONS_TAG);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.sendTextImage)
    public void onSendMessageClick(View view) {

        Message message = new Message(
            Auth.getInstance().getUsername(),
            messageEditText.getText().toString(),
            new Date().getTime()
        );

        MessageDB.insertMessages(this.recipients, message);
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void itemSelect(User user) {

        this.recipients.add(user);
        this.recipientsEditText.setText("");

        FragmentRecipientsView fragment = (FragmentRecipientsView) getSupportFragmentManager().findFragmentByTag(RECIPIENTS_TAG);
        fragment.onFriendAdded(user);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootLayout.getWindowToken(), 0);
    }

    @Override
    public void itemRemoved(int position) {

        this.recipients.remove(position);
    }
}
