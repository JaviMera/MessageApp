package com.teamtreehouse.ribbit.ui.fragments.inbox.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.DeleteTextCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.TextMessage;
import com.teamtreehouse.ribbit.ui.activities.ViewMessageActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewTextFragment extends ViewFragmentMessage implements ViewMessageFragment {

    private TextMessage friendMessage;

    @BindView(R.id.messageTextView)
    TextView messageTextView;

    public static ViewTextFragment newInstance(TextMessage message) {

        ViewTextFragment fragment = new ViewTextFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Message.KEY, message);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_text_fragment, container, false);

        ButterKnife.bind(this, view);

        friendMessage = getArguments().getParcelable(Message.KEY);
        messageTextView.setText(friendMessage.getText());

        // Because a text message will not be performing any network data when open
        // we don't need to worry about showing the progress bar while something loads
        this.parent.setProgressbarVisibility(View.GONE);
        this.parent.setCardViewVisibility(View.VISIBLE);

        // Start the timer as soon as the activity comes to the foreground
        this.parent.start();

        return view;
    }

    @Override
    public void onFinish() {

        MessageDB.deleteTextMessage(this.friendMessage.getId(), new DeleteTextCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
