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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.FirebaseImageLoader;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.models.users.UserRequest;
import com.teamtreehouse.ribbit.ui.activities.EditFriendActivity;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestionsView;
import com.teamtreehouse.ribbit.utils.GlideUtils;
import com.teamtreehouse.ribbit.utils.Resources;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentSuggestionsVH extends FragmentRecyclerVH<FragmentSuggestionsView,UserFriend> {

    private TextView usernameTextView;
    private CircleImageView userProfilePicture;

    public FragmentSuggestionsVH(FragmentSuggestionsView parent, View itemView) {
        super(parent, itemView);

        this.usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        this.userProfilePicture = (CircleImageView) itemView.findViewById(R.id.profilePictureView);
    }

    @Override
    public void bind(final UserFriend user) {

        MessageDB.readUser(user.getUsername(), new UserReadCallback() {
            @Override
            public void onUserRead(List<User> users) {

                User userInfo = users.get(0);

                if(userInfo.getPhotoUrl().isEmpty()) {

                    GlideUtils.loadDefault(((FragmentRecycler)fragment).getActivity(), userProfilePicture);
                }
                else {

                    StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(userInfo.getPhotoUrl());
                    GlideUtils.loadFromServer(((FragmentRecycler)fragment).getActivity(), ref, userProfilePicture);
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.onItemSelected(user);
            }
        });

        this.usernameTextView.setText(user.getDisplayName());
    }
}
