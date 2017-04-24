package com.teamtreehouse.ribbit.ui.fragments.suggestions;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.activities.ActivityView;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;

import java.util.List;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentSuggestions
    extends
        FragmentRecycler<ActivityView, FragmentSuggestionsPresenter, FragmentSuggestionsAdapter>
    implements
        FragmentSuggestionsView {

    public static FragmentSuggestions newInstance() {

        return new FragmentSuggestions();
    }

    @Override
    protected FragmentSuggestionsAdapter createAdapter() {

        return new FragmentSuggestionsAdapter(this);
    }

    @Override
    protected FragmentSuggestionsPresenter createPresenter() {

        return new FragmentSuggestionsPresenter(this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {

        return new LinearLayoutManager(this.getContext());
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_recycler;
    }

    @Override
    public void setItems(List<User> users) {

        FragmentSuggestionsAdapter adapter = getAdapter();
        adapter.clear();
        adapter.addAll(users);
    }

    @Override
    public void onItemSelected(User item) {

        Intent intent = new Intent();
        intent.putExtra("user", item);
        this.parent.itemSelect(intent);
    }
}
