package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.ui.fragments.inbox.InboxFragmentView;
import com.teamtreehouse.ribbit.utils.MessageUtils;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentMessagesVH extends FragmentRecyclerVH<Message> {

    private TextView messageTextView;
    private TextView dateTextView;

    public FragmentMessagesVH(InboxFragmentView parent, View view) {

        super(view);

        this.messageTextView = (TextView) view.findViewById(R.id.messageLabel);
        this.dateTextView = (TextView) view.findViewById(R.id.timeLabel);
    }

    @Override
    public void bind(Message message) {

        this.messageTextView.setText(message.getText());
        this.dateTextView.setText(MessageUtils.getTimeAgo(message.getDate()));
    }
}

