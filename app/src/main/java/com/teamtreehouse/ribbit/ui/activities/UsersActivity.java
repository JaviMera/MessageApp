package com.teamtreehouse.ribbit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.ui.fragments.search.FragmentSearch;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersActivity extends AppCompatActivity implements ActivityView {

    private SearchView searchView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
            public boolean onQueryTextChange(String username) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentSearch fragmentSearch = (FragmentSearch) fragmentManager.findFragmentById(R.id.container);

                if (username.isEmpty()) {

                    // Pass in an empty list of users, when an empty string is typed in the search view
                    fragmentSearch.addUsers(new LinkedList<User>());
                    return false;
                }

                MessageDB.filterUsers(username, new UserReadCallback() {

                    @Override
                    public void onUserRead(List<User> users) {

                        fragmentSearch.addUsers(users);
                    }
                });

                return false;
            }
        });

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, FragmentSearch.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public void itemSelect(Intent intent) {

        startActivity(intent);
    }
}
