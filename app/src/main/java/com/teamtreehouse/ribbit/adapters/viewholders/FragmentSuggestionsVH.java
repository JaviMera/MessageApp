package com.teamtreehouse.ribbit.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.ui.fragments.suggestions.FragmentSuggestionsView;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentSuggestionsVH extends FragmentRecyclerVH<FragmentSuggestionsView,UserFriend> {

    private TextView usernameTextView;

    public FragmentSuggestionsVH(FragmentSuggestionsView parent, View itemView) {
        super(parent, itemView);

        this.usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
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
