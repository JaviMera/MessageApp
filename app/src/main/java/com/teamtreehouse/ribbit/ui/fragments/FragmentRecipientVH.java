package com.teamtreehouse.ribbit.ui.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentRecyclerVH;
import com.teamtreehouse.ribbit.models.User;

/**
 * Created by javie on 4/10/2017.
 */

public class FragmentRecipientVH extends FragmentRecyclerVH<User> {

    private FragmentRecipientsView parent;
    TextView usernameText;
    ImageView recipientDeleteImage;

    public FragmentRecipientVH(FragmentRecipientsView parent, View view) {
        super(view);

        this.parent = parent;
        usernameText = (TextView) itemView.findViewById(R.id.userNameText);
        recipientDeleteImage = (ImageView) itemView.findViewById(R.id.deleteImage);
    }

    @Override
    public void bind(User userFriend) {

        recipientDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parent.onFriendRemoved(getAdapterPosition());
            }
        });
        usernameText.setText(userFriend.getUsername());
    }
}
