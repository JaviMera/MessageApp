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

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestionsView;
import com.teamtreehouse.ribbit.utils.Resources;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentSuggestionsVH extends FragmentRecyclerVH<FragmentSuggestionsView,UserFriend> {

    private TextView usernameTextView;
    private CircleImageView userImageView;

    public FragmentSuggestionsVH(FragmentSuggestionsView parent, View itemView) {
        super(parent, itemView);

        this.usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        this.userImageView = (CircleImageView) itemView.findViewById(R.id.userImageView);
    }

    @Override
    public void bind(final UserFriend friend) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.onItemSelected(friend);
            }
        });

        this.usernameTextView.setText(friend.getUsername());
    }
}
