package com.teamtreehouse.ribbit.ui.fragments.recipients;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentRecyclerVH;
import com.teamtreehouse.ribbit.models.users.User;

/**
 * Created by javie on 4/10/2017.
 */

public class FragmentRecipientVH extends FragmentRecyclerVH<FragmentRecipientsView, User> {

    TextView usernameText;
    ImageView recipientDeleteImage;

    public FragmentRecipientVH(FragmentRecipientsView parent, View view) {
        super(parent, view);

        usernameText = (TextView) itemView.findViewById(R.id.userNameText);
        recipientDeleteImage = (ImageView) itemView.findViewById(R.id.deleteImage);
    }

    @Override
    public void bind(User userFriend) {

        recipientDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.onFriendRemoved(getAdapterPosition());
            }
        });
        usernameText.setText(userFriend.getUsername());
    }
}
