package com.teamtreehouse.ribbit.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.ImageInsertCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.MessageStorage;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.inbox.messages.FragmentImageMessage;

import java.util.Date;
import java.util.UUID;

import butterknife.OnClick;

// TODO add tool bar to view image fragment layout
public class ImageMessageActivity extends MessageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri pictureUri = getIntent().getParcelableExtra("uri");
        replaceFragment(R.id.messageContainer, FragmentImageMessage.newInstance(pictureUri));
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

        this.presenter.setSendLayoutVisibility(View.GONE);
        this.presenter.setProgressbarVisibility(View.VISIBLE);

        FragmentImageMessage fragment = findFragmentById(R.id.messageContainer);
        Uri pictureUri = fragment.getValue();

        for(final User user : recipients) {

            MessageStorage.insertPicture(user.getId(), pictureUri, new ImageInsertCallback() {

                @Override
                public void onSuccess(String url, String path) {

                    ImageMessage message = new ImageMessage(
                        UUID.randomUUID().toString(),
                        Auth.getInstance().getUsername(),
                        url,
                        path,
                        new Date().getTime()
                    );

                    MessageDB.insertImageMessage(user.getId(), message, new ImageInsertCallback() {

                        @Override
                        public void onSuccess(String url, String path) {

                        }

                        @Override
                        public void onFailure() {

                            Toast
                                .makeText(
                                    ImageMessageActivity.this,
                                    "Unable to send image to " + user.getUsername(),
                                    Toast.LENGTH_SHORT)
                                .show();
                        }
                    });
                }

                @Override
                public void onFailure() {

                    Toast
                        .makeText(
                            ImageMessageActivity.this,
                            "Unable to send image",
                            Toast.LENGTH_SHORT)
                        .show();
                }
            });

            finish();
        }
    }
}
