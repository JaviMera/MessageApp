package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.recipients.FragmentRecipient;
import com.teamtreehouse.ribbit.ui.fragments.recipients.FragmentRecipientsView;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestions;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestionsView;
import com.teamtreehouse.ribbit.utils.Animations;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO add tool bar to view image fragment layout
public class ImageMessageActivity extends AppCompatActivity implements MessageActivityView{

    public static final String SUGGESTIONS_TAG = "suggestions";
    private static final String RECIPIENTS_TAG = "recipients";

    @BindView(R.id.root)
    RelativeLayout rootLayout;

    @BindView(R.id.recipientsEditText)
    EditText recipientsEditText;

    @BindView(R.id.textInput)
    EditText messageEditText;

    @BindView(R.id.sendTextImage)
    ImageView sendImageView;

    @BindView(R.id.imageView)
    ImageView pictureView;

    private List<User> recipients;
    private Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_message);

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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.recipientsContainer, FragmentRecipient.newInstance(), RECIPIENTS_TAG);
        transaction.replace(R.id.container, FragmentSuggestions.newInstance(), SUGGESTIONS_TAG);
        transaction.commit();

        pictureUri = getIntent().getParcelableExtra("picture");
        Picasso.with(this).load(pictureUri).into(this.pictureView);
//        transaction.replace(R.id.container, ViewImageFragment.newInstance(uri));
//        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // fragment, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.sendTextImage)
    public void onSendMessageClick(View view) {

        for(final User user : recipients) {

            FirebaseStorage
                .getInstance()
                .getReference()
                .child("images")
                .child(user.getId())
                .child(pictureUri.getLastPathSegment())
                .putFile(pictureUri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    @SuppressWarnings("VisibleForTests")
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = taskSnapshot.getMetadata().getDownloadUrl().toString();
                        String sender = Auth.getInstance().getUsername();
                        String path = taskSnapshot.getMetadata().getPath();
                        ImageMessage message = new ImageMessage(
                                UUID.randomUUID().toString(),
                                url, path, sender, new Date().getTime());

                        FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("messages")
                            .child("images")
                            .child(user.getId())
                            .push()
                            .setValue((message))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(ImageMessageActivity.this, "Uploaded Image!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    }
                });
        }
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
