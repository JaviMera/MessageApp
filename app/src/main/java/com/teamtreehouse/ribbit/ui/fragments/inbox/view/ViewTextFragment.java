package com.teamtreehouse.ribbit.ui.fragments.inbox.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.DeleteTextCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.messages.DefaultDuration;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.messages.MessageDuration;
import com.teamtreehouse.ribbit.models.messages.TextMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewTextFragment extends ViewFragmentMessage implements ViewMessageFragmentView {

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

        View view = inflater.inflate(R.layout.fragment_view_text, container, false);

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
    public MessageDuration getMessageDuration() {

        return new DefaultDuration();
    }

    @Override
    public void onFinish() {

        MessageDB.deleteTextMessage(currentUser.getId(), this.friendMessage.getId(), new DeleteTextCallback() {
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
