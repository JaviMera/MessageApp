package com.teamtreehouse.ribbit.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.ui.InboxFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 3/25/2017.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder>{

    private List<Message> messages;
    private Fragment context;

    public interface OnRecyclerViewClick {

        void onItemSingleClick(Message message);
    }

    public MessageRecyclerAdapter(Fragment context) {

        this.context = context;
        this.messages = new LinkedList<>();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
            .from(this.context.getActivity())
            .inflate(R.layout.message_item, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        holder.bind(this.messages.get(position));
    }

    @Override
    public int getItemCount() {

        return this.messages.size();
    }

    public void addMessages(List<Message> messages) {

        int start = this.messages.size();
        this.messages.addAll(messages);
        notifyItemRangeInserted(start, this.messages.size());
    }

    class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImageView;
        TextView nameLabel;
        TextView timeLabel;

        MessageViewHolder(View itemView) {
            super(itemView);

            iconImageView = (ImageView) itemView.findViewById(R.id.messageIcon);
            nameLabel = (TextView) itemView.findViewById(R.id.senderLabel);
            timeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
        }

        void bind(final Message message) {

            itemView.setOnClickListener(this);

            Date createdAt = message.getCreatedAt();
            long now = new Date().getTime();
            SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d");
            String convertedDate = format.format(createdAt);

            this.timeLabel.setText(convertedDate);

            if (message.getString(Message.KEY_FILE_TYPE).equals(Message.TYPE_IMAGE)) {

                this.iconImageView.setImageResource(R.drawable.ic_picture);

            } else {

                this.iconImageView.setImageResource(R.drawable.ic_video);
            }

            this.nameLabel.setText(message.getString(Message.KEY_SENDER_NAME));
        }

        @Override
        public void onClick(View view) {

            Message message = messages.get(getAdapterPosition());
            ((InboxFragment)context).onItemSingleClick(message);
        }
    }
}
