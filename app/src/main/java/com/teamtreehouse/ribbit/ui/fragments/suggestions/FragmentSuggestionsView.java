package com.teamtreehouse.ribbit.ui.fragments.suggestions;

import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecyclerView;

import java.util.List;

/**
 * Created by javie on 4/8/2017.
 */

public interface FragmentSuggestionsView extends FragmentRecyclerView {

    void setItems(List<User> users);
    void onItemSelected(User item);
}
