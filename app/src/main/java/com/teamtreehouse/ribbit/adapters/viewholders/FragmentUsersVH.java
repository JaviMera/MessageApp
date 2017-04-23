package com.teamtreehouse.ribbit.adapters.viewholders;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.FirebaseImageLoader;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerItemType;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.UserFactory;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;
import com.teamtreehouse.ribbit.ui.fragments.FragmentUsersView;
import com.teamtreehouse.ribbit.ui.fragments.recipients.FragmentRecipient;
import com.teamtreehouse.ribbit.ui.fragments.search.FragmentSearch;

/**
 * Created by javie on 4/3/2017.
 */

public abstract class FragmentUsersVH extends FragmentRecyclerVH<FragmentUsersView, User> {

    protected ImageView userProfilePicture;
    protected TextView userEmailTextView;
    protected Button negativeButtonView;
    protected Button positiveButtonView;
    private ImageView editContactImageView;

    UserFactory factory = new UserFactory();

    public FragmentUsersVH(FragmentUsersView fragment, View itemView) {
        super(fragment, itemView);

        userProfilePicture = (ImageView) itemView.findViewById(R.id.contactPicture);
        userEmailTextView = (TextView) itemView.findViewById(R.id.userEmailTextView);
        negativeButtonView = (Button) itemView.findViewById(R.id.negativeButtonView);
        positiveButtonView = (Button) itemView.findViewById(R.id.positiveButtonView);
        editContactImageView = (ImageView) itemView.findViewById(R.id.editContactImageView);
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void bind(final User user) {

        StorageReference ref = FirebaseStorage
            .getInstance()
            .getReference()
            .child("profile_pictures")
            .child(user.getId());

        Glide
            .with(((FragmentRecycler)fragment).getActivity())
            .using(new FirebaseImageLoader())
            .load(ref)
            .error(R.mipmap.ic_person)
            .fitCenter()
            .into(userProfilePicture);

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
