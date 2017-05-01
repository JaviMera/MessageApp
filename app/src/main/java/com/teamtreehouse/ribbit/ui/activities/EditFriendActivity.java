package com.teamtreehouse.ribbit.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamtreehouse.ribbit.FirebaseImageLoader;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.viewholders.FragmentFriendsVH;
import com.teamtreehouse.ribbit.database.DeleteFriendCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.UserReadCallback;
import com.teamtreehouse.ribbit.dialogs.DialogFragmentError;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserRequest;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;
import com.teamtreehouse.ribbit.utils.GlideUtils;

import java.util.List;

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

    @BindView(R.id.friendImageView)
    ImageView friendProfilePicture;

    private User friend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        friend = getIntent().getParcelableExtra(EDIT_FRIEND_KEY);
        username.setText(friend.getUsername());

        MessageDB.readUser(friend.getUsername(), new UserReadCallback() {
            @Override
            public void onUserRead(List<User> users) {

            User userInfo = users.get(0);
            if(userInfo.getPhotoUrl().isEmpty()) {

                GlideUtils.loadDefault(EditFriendActivity.this, friendProfilePicture);
            }
            else {

                StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(userInfo.getPhotoUrl());
                GlideUtils.loadFromServer(EditFriendActivity.this, ref, friendProfilePicture);
            }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_edit_friend_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

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

        final User user = Auth.getInstance().getUser();
        MessageDB.deleteFriend(user, friend, new DeleteFriendCallback() {
            @Override
            public void onDeleteFriend() {

                NavUtils.navigateUpFromSameTask(EditFriendActivity.this);
            }
        });
    }
}
