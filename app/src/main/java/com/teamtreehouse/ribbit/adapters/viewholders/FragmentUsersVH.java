package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerItemType;
import com.teamtreehouse.ribbit.models.UserFactory;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;

/**
 * Created by javie on 4/3/2017.
 */

public abstract class FragmentUsersVH extends FragmentRecyclerVH<FragmentUsersView, User> {

    protected TextView userEmailTextView;
    protected Button negativeButtonView;
    protected Button positiveButtonView;
    private ImageView editContactImageView;

    UserFactory factory = new UserFactory();

    public FragmentUsersVH(FragmentUsersView fragment, View itemView) {
        super(fragment, itemView);

        userEmailTextView = (TextView) itemView.findViewById(R.id.userEmailTextView);
        negativeButtonView = (Button) itemView.findViewById(R.id.negativeButtonView);
        positiveButtonView = (Button) itemView.findViewById(R.id.positiveButtonView);
        editContactImageView = (ImageView) itemView.findViewById(R.id.editContactImageView);
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void bind(final User user) {

        userEmailTextView.setText(user.getUsername());

        final RecyclerItemType viewHolder = factory.getViewHolder(user);

        negativeButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            fragment.onInviteClick(viewHolder.getNegativeButtonResponses(), getAdapterPosition());
            }
        });

        positiveButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            fragment.onInviteClick(viewHolder.getPositiveButtonResponses(), getAdapterPosition());
            }
        });

        editContactImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            fragment.onItemSelected(user);
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
