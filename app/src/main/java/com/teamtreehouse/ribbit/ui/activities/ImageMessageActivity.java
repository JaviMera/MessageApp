package com.teamtreehouse.ribbit.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentImageMessage;
import com.teamtreehouse.ribbit.utils.FileHelper;

import java.util.Date;
import java.util.UUID;

import butterknife.OnClick;

// TODO add tool bar to view image fragment layout
public class ImageMessageActivity extends MessageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri pictureUri = getIntent().getParcelableExtra("uri");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.messageContainer, FragmentImageMessage.newInstance(pictureUri));
        fragmentTransaction.commit();
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

        showProgress();

        FragmentImageMessage fragment = (FragmentImageMessage) getSupportFragmentManager().findFragmentById(R.id.messageContainer);
        Uri pictureUri = fragment.getUri();

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
}
