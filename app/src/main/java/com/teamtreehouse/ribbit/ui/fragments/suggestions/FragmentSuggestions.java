package com.teamtreehouse.ribbit.ui.fragments.suggestions;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.activities.ActivityBase;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;

import java.util.List;

/**
 * Created by javie on 4/8/2017.
 */

public class FragmentSuggestions
    extends
        FragmentRecycler<ActivityBase, FragmentSuggestionsPresenter, FragmentSuggestionsAdapter>
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

        return R.layout.text_message_fragment;
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
