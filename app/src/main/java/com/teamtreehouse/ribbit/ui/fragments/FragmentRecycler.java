package com.teamtreehouse.ribbit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class FragmentRecycler<P extends FragmentRecyclerPresenter, A extends RecyclerAdapter> extends Fragment
    implements FragmentRecyclerView{

    protected abstract A getAdapter();
    protected abstract P getPresenter();
    protected abstract int getLayout();

    protected P presenter;

    @BindView(R.id.recycler)
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(getLayout(), container, false);

        ButterKnife.bind(this, view);

        presenter = getPresenter();
        presenter.setRecyclerAdapter(getAdapter());
        presenter.setRecyclerLayout(new LinearLayoutManager(getContext()));
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
}
