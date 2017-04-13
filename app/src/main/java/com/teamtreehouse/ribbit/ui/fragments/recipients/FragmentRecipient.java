package com.teamtreehouse.ribbit.ui.fragments.recipients;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.FragmentRecipientsAdapter;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.ui.activities.MessageActivityView;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;

/**
 * Created by javie on 4/10/2017.
 */

public class FragmentRecipient
    extends
        FragmentRecycler<MessageActivityView, FragmentRecipientsPresenter, FragmentRecipientsAdapter>
    implements
        FragmentRecipientsView {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.parent = (MessageActivityView) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        this.recyclerView.setVisibility(View.GONE);
        return view;
    }

    @Override
    protected FragmentRecipientsAdapter createAdapter() {
        return new FragmentRecipientsAdapter(this);
    }

    @Override
    protected FragmentRecipientsPresenter createPresenter() {
        return new FragmentRecipientsPresenter(this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {

        return new GridLayoutManager(this.getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recycler;
    }

    @Override
    public void onFriendAdded(User user) {

        FragmentRecipientsAdapter adapter = getAdapter();

        if(adapter.getItemCount() == 0) {

            this.recyclerView.setVisibility(View.VISIBLE);
            this.parent.showSendButton();
            this.parent.showInputEditText();
        }

        adapter.add(user);
    }

    @Override
    public void onFriendRemoved(int adapterPosition) {

        FragmentRecipientsAdapter adapter = getAdapter();
        adapter.removeItem(adapterPosition);
        this.parent.recipientRemoved(adapterPosition);

        if(adapter.getItemCount() == 0) {

            this.recyclerView.setVisibility(View.GONE);
            this.parent.hideSendButton();
            this.parent.hideInputEditText();
        }
    }

    @Override
    public void onItemSelected(User user) {

    }

    public static Fragment newInstance() {

        return new FragmentRecipient();
    }
}
