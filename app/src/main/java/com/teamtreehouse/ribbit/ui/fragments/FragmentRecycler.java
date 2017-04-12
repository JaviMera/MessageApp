package com.teamtreehouse.ribbit.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.ui.activities.ActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class FragmentRecycler<TActivity extends ActivityView, TPresenter extends FragmentRecyclerPresenter, TAdapter extends RecyclerAdapter>
    extends
        Fragment
    implements
        FragmentRecyclerView{

    protected abstract TAdapter createAdapter();
    protected abstract TPresenter createPresenter();
    protected abstract RecyclerView.LayoutManager createLayoutManager();
    protected abstract int getLayout();

    protected TPresenter presenter;
    protected TActivity parent;

    @BindView(R.id.recycler)
    protected RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.parent = (TActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(getLayout(), container, false);

        ButterKnife.bind(this, view);

        presenter = createPresenter();
        presenter.setRecyclerAdapter(createAdapter());
        presenter.setRecyclerLayout(createLayoutManager());
        presenter.setRecyclerFixedSize(true);

        return view;
    }

    @Override
    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setRecyclerLayout(RecyclerView.LayoutManager layoutManager) {

        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setRecyclerFixedSize(boolean fixedSize) {

        recyclerView.setHasFixedSize(fixedSize);
    }

    protected TAdapter getAdapter() {

        return (TAdapter) this.recyclerView.getAdapter();
    }
}
