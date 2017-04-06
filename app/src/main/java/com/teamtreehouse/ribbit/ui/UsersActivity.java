package com.teamtreehouse.ribbit.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.RecyclerActivityView;
import com.teamtreehouse.ribbit.adapters.RecyclerAdapter;
import com.teamtreehouse.ribbit.adapters.UserActivityAdapter;
import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserInvite;
import com.teamtreehouse.ribbit.models.UserRequest;
import com.teamtreehouse.ribbit.ui.callbacks.FriendsCallback;
import com.teamtreehouse.ribbit.ui.callbacks.InvitesCallback;
import com.teamtreehouse.ribbit.ui.callbacks.PendingCallback;
import com.teamtreehouse.ribbit.ui.callbacks.RecipientListener;
import com.teamtreehouse.ribbit.ui.callbacks.SenderListener;
import com.teamtreehouse.ribbit.ui.callbacks.UserFilterCallback;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class UsersActivity extends AppCompatActivity
    implements
    RecyclerActivityView,
    UserFilterCallback.UserListener,
    SenderListener,
    RecipientListener,
    FriendsCallback.OnFriendsListener{

    private UserFilterCallback userFilterCallback;
    private PendingCallback pendingCallback;
    private FriendsCallback friendsCallback;
    private InvitesCallback invitesCallback;

    private HashMap<String, User> friends = new LinkedHashMap<>();
    private HashMap<String, User> invites = new LinkedHashMap<>();

    private RecyclerView recycler;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_users_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String userEmail) {

                RecyclerAdapter adapter = (RecyclerAdapter) recycler.getAdapter();
                adapter.clear();
                userFilterCallback.filterUsers(userEmail, friends, invites);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userFilterCallback = new UserFilterCallback(this);
        pendingCallback = new PendingCallback(this);
        invitesCallback = new InvitesCallback(this);
        friendsCallback = new FriendsCallback(this);

        recycler = (RecyclerView) findViewById(R.id.recycler);

        RecyclerAdapter adapter = new UserActivityAdapter(this);
        recycler.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
    }

    @Override
    public void onUserAdded(User user) {

        if(user != null) {

            RecyclerAdapter adapter = (RecyclerAdapter) recycler.getAdapter();

            if(!adapter.contains(user))
                adapter.addUser(user);
        }
    }

    @Override
    public void onButtonClick(List<ButtonAction> buttonActions, int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) recycler.getAdapter();
        User user = adapter.getItem(position);

        for(ButtonAction buttonAction : buttonActions) {

            buttonAction.execute(user);
        }
    }

    @Override
    public void onFriendAdded(User userFriend) {

        friends.put(userFriend.getId(), userFriend);
    }

    @Override
    public void onPendingAdded(User user) {

        invites.put(user.getId(), user);

        RecyclerAdapter adapter = (RecyclerAdapter) recycler.getAdapter();
        int position = adapter.getPosition(user);

        if(position == -1)
            return;

        if(adapter.getItem(position) instanceof UserFriend)
            return;

        // When an invitation is sent, change the current item to a UserSender type
        adapter.changeItem(user, position);
    }

    @Override
    public void onInvitesAdded(User userInvite) {

        invites.put(userInvite.getId(), userInvite);

        // When the current user sends an invite, check if the receiver of this invite
        // also happens to be looking for the sender user.
        RecyclerAdapter adapter = (RecyclerAdapter) recycler.getAdapter();
        if(adapter.contains(userInvite)) {

            // If so, then update their screen by displaying the sender user as a User Invite type,
            // instead of just showing it as a User Request type
            int position = adapter.getPosition(userInvite);
            adapter.changeItem(userInvite, position);
        }
    }

    @Override
    public void onChanged(UserInvite user) {

        RecyclerAdapter adapter = (RecyclerAdapter) recycler.getAdapter();
        int position = adapter.getPosition(user);

        if(user.getStatus() == 1) {

            // When an invitation is accepted, change the current item to a UserFriend type
            adapter.changeItem(new UserFriend(user.getId(), user.getUsername()), position);
        }
        else if(user.getStatus() == 2) {

            adapter.changeItem(new UserRequest(user.getId(), user.getUsername()), position);
        }
    }
}
