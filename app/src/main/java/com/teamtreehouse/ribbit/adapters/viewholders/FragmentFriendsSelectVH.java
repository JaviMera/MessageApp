package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.UserSelectable;
import com.teamtreehouse.ribbit.ui.fragments.selectfriends.FragmentFriendsSelectView;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentFriendsSelectVH extends FragmentRecyclerVH<UserSelectable> {

    private FragmentFriendsSelectView parent;

    private CheckBox selectCheckBox;
    private TextView usernameTextView;

    public FragmentFriendsSelectVH(FragmentFriendsSelectView parent, View itemView) {
        super(itemView);

        this.parent = parent;

        this.selectCheckBox = (CheckBox) itemView.findViewById(R.id.selectCheckBox);
        this.usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
    }

    @Override
    public void bind(final UserSelectable friend) {

        this.selectCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked) {

                    parent.onFriendSelected(friend);
                }
                else {

                    parent.onFriendDeselected(friend);
                }
            }
        });

        this.usernameTextView.setText(friend.getUsername());
    }
}
