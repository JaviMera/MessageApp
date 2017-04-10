package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.ui.fragments.selectfriends.FragmentFriendsSelectView;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentFriendsSelectVH extends FragmentRecyclerVH<UserFriend> {

    private FragmentFriendsSelectView parent;

    private TextView usernameTextView;

    public FragmentFriendsSelectVH(FragmentFriendsSelectView parent, View itemView) {
        super(itemView);

        this.parent = parent;
        this.usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
    }

    @Override
    public void bind(final UserFriend friend) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parent.onFriendSelected(friend);
            }
        });

        this.usernameTextView.setText(friend.getUsername());
    }
}
