package com.teamtreehouse.ribbit.ui.fragments.suggestions;

import android.view.View;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentSuggestionsVH;
import com.teamtreehouse.ribbit.models.users.User;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentSuggestionsAdapter extends RecyclerAdapter<User, FragmentSuggestionsView> {

    public FragmentSuggestionsAdapter(FragmentSuggestionsView parent) {
        super(parent);
    }

    @Override
    protected FragmentSuggestionsVH getViewHolder(FragmentSuggestionsView parent, View view) {

        return new FragmentSuggestionsVH(parent, view);
    }

    @Override
    protected int getItemLayout() {

        return R.layout.friends_select_item;
    }
}
