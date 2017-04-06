package com.teamtreehouse.ribbit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.FragmentRecyclerView;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.InviteStatus;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.InvitesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.RecipientListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/6/2017.
 */

public abstract class FragmentUsers extends Fragment
    implements
    FragmentUsersView,
    RecipientListener,
    FriendsCallback.OnFriendsListener{

    protected abstract void acceptAdapterAction(UserInvite user, int position);
    protected abstract void rejectAdapterAction(UserInvite user, int position);
    protected abstract RecyclerAdapter getAdapter();

    private InvitesCallback invitesCallback;
    private FriendsCallback friendsCallback;
    private FragmentUsersPresenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        presenter = new FragmentUsersPresenter(this);
        presenter.setRecyclerAdapter(getAdapter());
        presenter.setRecyclerLayout(new LinearLayoutManager(getContext()));
        presenter.setRecyclerFixedSize(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.invitesCallback = new InvitesCallback(this);
        this.friendsCallback = new FriendsCallback(this);
    }

    @Override
    public void onButtonClick(List<ButtonAction> buttonActions, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        User user = adapter.getItem(position);

        for(ButtonAction buttonAction : buttonActions) {

            buttonAction.execute(user);
        }
    }

    @Override
    public void onChanged(UserInvite user) {

        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        int position = adapter.getPosition(user);

        if(user.getStatus() == InviteStatus.Accepted.ordinal()) {

            // When an invitation is accepted, change the current item to a UserFriend type
            acceptAdapterAction(user, position);
        }
        else if(user.getStatus() == InviteStatus.Rejected.ordinal()) {

            rejectAdapterAction(user, position);
        }
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
