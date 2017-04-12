package com.teamtreehouse.ribbit.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.dialogs.DialogFragmentError;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/7/2017.
 */

public class EditFriendActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    public static final String EDIT_FRIEND_KEY = "user";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.username)
    TextView username;

    private User friend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        friend = getIntent().getParcelableExtra(EDIT_FRIEND_KEY);
        username.setText(friend.getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_edit_friend_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_delete:

                DialogFragmentError dialogFragmentError = DialogFragmentError.newInstance(
                    getString(R.string.delete_friend_message), "Warning!");

                dialogFragmentError.show(getSupportFragmentManager(), "delete_friend_dialog");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        MessageDB.deleteFriend(Auth.getInstance().getUser(), friend);
        finish();
    }
}
