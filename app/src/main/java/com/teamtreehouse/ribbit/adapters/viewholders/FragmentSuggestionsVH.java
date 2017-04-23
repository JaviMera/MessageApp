package com.teamtreehouse.ribbit.adapters.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamtreehouse.ribbit.FirebaseImageLoader;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestionsView;
import com.teamtreehouse.ribbit.utils.Resources;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentSuggestionsVH extends FragmentRecyclerVH<FragmentSuggestionsView,UserFriend> {

    private TextView usernameTextView;
    private CircleImageView friendProfilePicture;

    public FragmentSuggestionsVH(FragmentSuggestionsView parent, View itemView) {
        super(parent, itemView);

        this.usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        this.friendProfilePicture = (CircleImageView) itemView.findViewById(R.id.profilePictureView);
    }

    @Override
    public void bind(final UserFriend friend) {

        StorageReference ref = FirebaseStorage
            .getInstance()
            .getReference()
            .child("profile_pictures")
            .child(friend.getId());

        Glide
            .with(((FragmentRecycler)fragment).getActivity())
            .using(new FirebaseImageLoader())
            .load(ref)
            .error(R.drawable.person)
            .fitCenter()
            .into(friendProfilePicture);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.onItemSelected(friend);
            }
        });

        this.usernameTextView.setText(friend.getUsername());
    }
}
