package com.teamtreehouse.ribbit.ui.activities.messages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.ImageMessage;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.MultimediaMessage;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.fragments.inbox.messages.FragmentImageMessage;

import java.util.ArrayList;

import butterknife.OnClick;

// TODO add tool bar to view image fragment layout
public class ImageMessageActivity extends MessageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri pictureUri = getIntent().getParcelableExtra(MultimediaMessage.KEY);
        replaceFragment(R.id.messageContainer, FragmentImageMessage.newInstance(pictureUri));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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

        Intent serviceIntent = new Intent(this, ImageMessageService.class);
        serviceIntent.putExtra(ImageMessage.KEY, pictureUri);
        serviceIntent.putParcelableArrayListExtra(User.KEY, new ArrayList<>(this.recipients));
        startService(serviceIntent);
    }
}
