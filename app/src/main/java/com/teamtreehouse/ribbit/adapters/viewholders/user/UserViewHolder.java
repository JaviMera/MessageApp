package com.teamtreehouse.ribbit.adapters.viewholders.user;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.FragmentRecyclerView;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.FragmentUsersView;

/**
 * Created by javie on 4/3/2017.
 */

public abstract class UserViewHolder extends RecyclerView.ViewHolder {
    
    protected FragmentUsersView activity;
    protected TextView userEmailTextView;
    protected Button negativeButtonView;
    protected Button positiveButtonView;
    private ImageView editContactImageView;

    UserFactory factory = new UserFactory();

    public UserViewHolder(FragmentUsersView activity, View itemView) {
        super(itemView);

        this.activity = activity;
        userEmailTextView = (TextView) itemView.findViewById(R.id.userEmailTextView);
        negativeButtonView = (Button) itemView.findViewById(R.id.negativeButtonView);
        positiveButtonView = (Button) itemView.findViewById(R.id.positiveButtonView);
        editContactImageView = (ImageView) itemView.findViewById(R.id.editContactImageView);
    }

    @SuppressWarnings("ResourceType")
    public void bind(final User user) {

        userEmailTextView.setText(user.getUsername());

        final RecyclerItemType viewHolder = factory.getViewHolder(user);

        negativeButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            activity.onButtonClick(viewHolder.getNegativeButtonResponses(), getAdapterPosition());
            }
        });

        positiveButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            activity.onButtonClick(viewHolder.getPositiveButtonResponses(), getAdapterPosition());
            }
        });

        negativeButtonView.setVisibility(viewHolder.cancelButtonVisibility());
        positiveButtonView.setVisibility(viewHolder.acceptButtonVisibility());
        negativeButtonView.setText(viewHolder.cancelButtonText());
        positiveButtonView.setText(viewHolder.acceptButtonText());
        positiveButtonView.setEnabled(viewHolder.setAcceptButtonEnabled());
        editContactImageView.setVisibility(viewHolder.editImageVisibility());
    }
}