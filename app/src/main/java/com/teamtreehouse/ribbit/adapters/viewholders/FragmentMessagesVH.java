package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.ui.fragments.inbox.FragmentMessagesView;
import com.teamtreehouse.ribbit.utils.MessageUtils;

/**
 * Created by javie on 4/6/2017.
 */

public class FragmentMessagesVH extends FragmentRecyclerVH<FragmentMessagesView, Message> {

    private TextView messageTextView;
    private TextView dateTextView;

    public FragmentMessagesVH(FragmentMessagesView parent, View view) {

        super(parent, view);

        this.messageTextView = (TextView) view.findViewById(R.id.messageLabel);
        this.dateTextView = (TextView) view.findViewById(R.id.timeLabel);
    }

    @Override
    public void bind(final Message message) {

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.onItemSelected(message);
            }
        });
        this.messageTextView.setText(message.getText());
        this.dateTextView.setText(MessageUtils.getTimeAgo(message.getDate()));
    }
}

