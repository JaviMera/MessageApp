package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.ui.fragments.inbox.FragmentInboxView;
import com.teamtreehouse.ribbit.utils.MessageUtils;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentMessagesVH extends FragmentRecyclerVH<FragmentInboxView, Message> {

    private TextView messageTextView;
    private TextView dateTextView;
    private TextView messageTypeText;

    @Override
    public void bind(final Message message) {

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.onItemSelected(message);
            }
        });

        this.messageTextView.setText(message.getDisplayName());
        this.dateTextView.setText(MessageUtils.getTimeAgo(message.getDate()));
        this.messageTypeText.setText(message.type());
    }

    public FragmentMessagesVH(FragmentInboxView parent, View view) {

        super(parent, view);

        this.messageTextView = (TextView) view.findViewById(R.id.usernameLabel);
        this.dateTextView = (TextView) view.findViewById(R.id.timeLabel);
        this.messageTypeText = (TextView) view.findViewById(R.id.messageTypeText);
    }
}

