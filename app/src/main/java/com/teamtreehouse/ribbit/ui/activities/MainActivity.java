package com.teamtreehouse.ribbit.ui.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.SectionsPagerAdapter;
import com.teamtreehouse.ribbit.animations.TextViewAlphaIn;
import com.teamtreehouse.ribbit.animations.TextViewAlphaOut;
import com.teamtreehouse.ribbit.animations.ViewAnimationCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.MessageStorage;
import com.teamtreehouse.ribbit.database.MultimediaStorageCallback;
import com.teamtreehouse.ribbit.database.UpdatePictureCallback;
import com.teamtreehouse.ribbit.dialogs.DialogFragmentError;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.CameraActivityInfo;
import com.teamtreehouse.ribbit.models.CaptureVideoInfo;
import com.teamtreehouse.ribbit.models.MessagePicture;
import com.teamtreehouse.ribbit.models.ProfilePicture;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.InboxService;
import com.teamtreehouse.ribbit.ui.activities.intents.MultimediaResultIntent;
import com.teamtreehouse.ribbit.models.messages.MultimediaMessage;
import com.teamtreehouse.ribbit.ui.activities.intents.PictureCaptureResultIntent;
import com.teamtreehouse.ribbit.ui.activities.intents.PicturePickResultIntent;
import com.teamtreehouse.ribbit.ui.activities.intents.VideoCaptureResultIntent;
import com.teamtreehouse.ribbit.ui.activities.intents.VideoPickResultIntent;
import com.teamtreehouse.ribbit.animations.FabDown;
import com.teamtreehouse.ribbit.animations.FabUp;
import com.teamtreehouse.ribbit.ui.activities.messages.MessageService;
import com.teamtreehouse.ribbit.ui.activities.messages.TextMessageActivity;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.fragments.friends.FragmentFriends;
import com.teamtreehouse.ribbit.ui.fragments.inbox.FragmentInbox;
import com.teamtreehouse.ribbit.utils.Animations;
import com.teamtreehouse.ribbit.utils.FileHelper;
import com.teamtreehouse.ribbit.utils.GlideUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity
    extends
        AppCompatActivity
    implements
        MainActivityView,
        ViewPager.OnPageChangeListener,
        NavigationView.OnNavigationItemSelectedListener {

    public static final int ITEM_SELECT_CODE = 1000;
    private static int[] fabIcons = new int[]{
            R.mipmap.ic_message, R.mipmap.ic_add_contact
    };

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private HashMap<Integer, MultimediaResultIntent> intentTypes;
    private CircleImageView userProfilePictureView;
    private TextView usernameTextView;

    SectionsPagerAdapter viewPagerAdapter;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.fabTextLabel)
    TextView fabTextLabel;

    @BindView(R.id.fabPictureLabel)
    TextView fabPictureLabel;

    @BindView(R.id.fabCapturePictureLabel)
    TextView fabCapturePictureLabel;

    @BindView(R.id.fabVideoLabel)
    TextView fabVideoLabel;

    @BindView(R.id.fabCaptureVideoLabel)
    TextView fabCaptureVideoLabel;

    @BindView(R.id.fabPictureMessage)
    FloatingActionButton pictureFab;

    @BindView(R.id.fabCapturePictureMessage)
    FloatingActionButton capturePictureFab;

    @BindView(R.id.fabVideoMessage)
    FloatingActionButton videoFab;

    @BindView(R.id.fabCaptureVideoMessage)
    FloatingActionButton captureVideoFab;

    @BindView(R.id.fabTextMesssage)
    FloatingActionButton textFab;

    private MainActivityPresenter presenter;
    private FragmentPager currentFragment;
    private boolean fabMenuOpen;
    private ActionBarDrawerToggle toggle;
    private InboxService service;
    private boolean bound;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {

            InboxService.InboxBinder serviceBinder = (InboxService.InboxBinder) binder;
            service = serviceBinder.getService();
            service.setWithPendingIntent(false);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.intentTypes = new HashMap<>();
        this.intentTypes.put(getResources().getInteger(R.integer.message_pick_picture_request), new PicturePickResultIntent());
        this.intentTypes.put(getResources().getInteger(R.integer.message_pick_video_request), new VideoPickResultIntent());
        this.intentTypes.put(getResources().getInteger(R.integer.message_take_picture_request), new PictureCaptureResultIntent());
        this.intentTypes.put(getResources().getInteger(R.integer.message_take_video_request), new VideoCaptureResultIntent());

        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Auth.getInstance().getUsername());

        viewPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager(), new FragmentInbox(), new FragmentFriends());

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(viewPagerAdapter);

        // Tell the tab layout to set the view pager object
        tabLayout.setupWithViewPager(viewPager);

        // Setup icons to display for each tab
        for (int tab = 0; tab < tabLayout.getTabCount(); tab++) {

            TabLayout.Tab currentTab = tabLayout.getTabAt(tab);

            if (currentTab != null) {

                currentTab.setIcon(viewPagerAdapter.getIcon(tab));
            }
        }

        viewPager.addOnPageChangeListener(this);

        fab.setImageResource(fabIcons[0]);
        this.currentFragment = this.viewPagerAdapter.getItem(0);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);

        this.usernameTextView = (TextView)this.navigationView.getHeaderView(0).findViewById(R.id.usernameText);
        this.userProfilePictureView = (CircleImageView) this.navigationView.getHeaderView(0).findViewById(R.id.profilePictureView);
        this.usernameTextView.setText(Auth.getInstance().getUsername());

        User currentUser = Auth.getInstance().getUser();
        if(currentUser.getPhotoUrl().isEmpty()) {

            GlideUtils.loadDefault(this, userProfilePictureView);
        }
        else {

            StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(currentUser.getPhotoUrl());
            GlideUtils.loadFromServer(this, ref, userProfilePictureView);
        }

        this.navigationView
            .getHeaderView(0)
            .findViewById(R.id.capturePicture)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean granted = checkPermissions(PERMISSIONS_STORAGE);

                    // Check if permissions to read/write from/to external storage are granted
                    if(!granted) {

                        // Request permissions to the user
                        requestPermissions(PERMISSIONS_STORAGE);
                        return;
                    }

                    // Check if an external storage of any type is mounted on the device
                    if (!isExternalStorageAvailable()) {
                        return;
                    }

                    launchCameraActivity(new ProfilePicture(MainActivity.this));
                }
            });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private BroadcastReceiver messageBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        String result = intent.getStringExtra(Message.KEY);
        Toast
            .makeText(
                MainActivity.this,
                result,
                Toast.LENGTH_SHORT)
            .show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, InboxService.class);
        startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(MessageService.MESSAGE_ACTION_VIEW);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageBroadcast, filter);
    }



    @OnClick(R.id.fab)
    public void onFabClick(View view) {

        this.currentFragment.execute();

        overridePendingTransition(R.anim.slide_top_to_bottom, R.anim.slide_bottom_to_top);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (resultCode == RESULT_OK) {

            if(requestCode == getResources().getInteger(R.integer.profile_take_picture_request)) {

                final Bitmap picture = (Bitmap) data.getExtras().get("data");
                final User user = Auth.getInstance().getUser();
                Uri uri = Uri.parse(FileHelper.getPath(picture, this));

                MessageStorage.updateProfilePicture(user.getId(), uri, new MultimediaStorageCallback() {
                    @Override
                    public void onSuccess(String url, String path) {

                        final HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("photoUrl", url);

                        MessageDB.updateProfilePicture(user.getUsername(), map, new UpdatePictureCallback() {
                            @Override
                            public void onSuccess() {

                                userProfilePictureView.setImageBitmap(picture);
                            }

                            @Override
                            public void onFailure() {

                                DialogFragmentError dialog = DialogFragmentError.newInstance(
                                    getString(R.string.profile_picture_error_message),
                                    getString(R.string.profile_picture_error_title)
                                );

                                dialog.show(getSupportFragmentManager(), "error_dialog");
                            }
                        });
                    }

                    @Override
                    public void onFailure(String message) {

                        DialogFragmentError dialog = DialogFragmentError.newInstance(
                                getString(R.string.profile_picture_error_message),
                                getString(R.string.profile_picture_error_title)
                        );

                        dialog.show(getSupportFragmentManager(), "error_dialog");
                    }
                });
            }
            else {

                MultimediaResultIntent intentType = this.intentTypes.get(requestCode);

                Intent intent = new Intent(MainActivity.this, intentType.getActivity());
                intent.putExtra(MultimediaMessage.KEY, intentType.getUri(data, this));
                startActivity(intent);
            }
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        // change this intent so that the usernameText can't hit "back" and getValue into the inbox

        // Set these flags to finish any other tasks that are related to Main Activity
        // This way when login fragment is launched, there is no previous fragment to go back to,
        // and this exiting the app by pressing the back loginButton.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout:

                this.bound = false;
                this.service.stopSelf();
                unbindService(this.connection);

                FirebaseAuth
                    .getInstance()
                    .signOut();

                navigateToLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(bound) {

            bound = false;
            service.setWithPendingIntent(true);
            unbindService(connection);
        }
    }

    @Override
    public void itemSelect(Intent intent) {

        startActivityForResult(intent, ITEM_SELECT_CODE);
    }

    @Override
    public void requestPermissions(String... permissions) {

        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
                this,
                permissions,
                REQUEST_EXTERNAL_STORAGE
        );
    }

    @Override
    public void setFabIcon(int icon) {

        this.fab.setImageDrawable(ContextCompat.getDrawable(this, icon));
    }

    private boolean isExternalStorageAvailable() {

        String state = Environment.getExternalStorageState();

        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @OnClick(R.id.fabTextMesssage)
    public void onTextMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);
        Intent messageIntent = new Intent(this, TextMessageActivity.class);
        startActivity(messageIntent);
    }

    @OnClick(R.id.fabPictureMessage)
    public void onPictureMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);

        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        choosePhotoIntent.setType(getString(R.string.image_type));
        startActivityForResult(choosePhotoIntent, getResources().getInteger(R.integer.message_pick_picture_request));
    }

    @OnClick(R.id.fabCapturePictureMessage)
    public void onCapturePictureMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);
        if (isExternalStorageAvailable()) {

           launchCameraActivity(new MessagePicture(this));
        }
    }

    @OnClick(R.id.fabVideoMessage)
    public void onVideoMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);
        Intent chooseVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseVideoIntent.setType(getString(R.string.video_type));
        startActivityForResult(chooseVideoIntent, getResources().getInteger(R.integer.message_pick_video_request));
    }

    @OnClick(R.id.fabCaptureVideoMessage)
    public void onVideoCaptureMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);
        if (isExternalStorageAvailable()) {

            launchCameraActivity(new CaptureVideoInfo(this));
        }
    }

    @Override
    public void showFabMenu() {

        fabMenuOpen = true;
        Animations.scale(new FabUp(textFab, 1.0f, 500), new ViewAnimationCallback() {
            @Override
            public void onFinish() {
                Animations.alpha(new TextViewAlphaIn(fabTextLabel, 1.0f, 100));
            }
        });
        Animations.scale(new FabUp(pictureFab, 1.0f, 400), new ViewAnimationCallback() {
            @Override
            public void onFinish() {
                Animations.alpha(new TextViewAlphaIn(fabPictureLabel, 1.0f, 100));
            }
        });
        Animations.scale(new FabUp(capturePictureFab, 1.0f, 300), new ViewAnimationCallback() {
            @Override
            public void onFinish() {
                Animations.alpha(new TextViewAlphaIn(fabCapturePictureLabel, 1.0f, 100));
            }
        });
        Animations.scale(new FabUp(videoFab, 1.0f, 200), new ViewAnimationCallback() {
            @Override
            public void onFinish() {
                Animations.alpha(new TextViewAlphaIn(fabVideoLabel, 1.0f, 100));
            }
        });
        Animations.scale(new FabUp(captureVideoFab, 1.0f, 100), new ViewAnimationCallback() {
            @Override
            public void onFinish() {
                Animations.alpha(new TextViewAlphaIn(fabCaptureVideoLabel, 1.0f, 100));
            }
        });
    }

    @Override
    public void hideFabMenu() {

        fabMenuOpen = false;
        Animations.scale(new FabDown(textFab, 0.0f, 100), null);
        Animations.alpha(new TextViewAlphaOut(fabTextLabel, 0.0f, 100));
        Animations.scale(new FabDown(pictureFab, 0.0f, 200), null);
        Animations.alpha(new TextViewAlphaIn(fabPictureLabel, 0.0f, 100));
        Animations.scale(new FabDown(capturePictureFab, 0.0f, 300), null);
        Animations.alpha(new TextViewAlphaIn(fabCapturePictureLabel, 0.0f, 100));
        Animations.scale(new FabDown(videoFab, 0.0f, 400), null);
        Animations.alpha(new TextViewAlphaIn(fabVideoLabel, 0.0f, 100));
        Animations.scale(new FabDown(captureVideoFab, 0.0f, 500), null);
        Animations.alpha(new TextViewAlphaIn(fabCaptureVideoLabel, 0.0f, 100));
    }

    @Override
    public int getFabMenuVisibility() {

        return fabMenuOpen ? View.VISIBLE : View.GONE;
    }

    @Override
    public boolean checkPermissions(String[] permissions) {

        for (String permission : permissions) {

            if (ContextCompat
                    .checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

                return false;
            }
        }

        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        hideFabMenu();
        fab.setImageResource(fabIcons[position]);
        currentFragment = viewPagerAdapter.getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }

    private void launchCameraActivity(CameraActivityInfo info) {

        // 1. Get the external storage directory
        File mediaStorageDir = info.getDir();

        // 2. Create a unique file name
        String timeStamp = new SimpleDateFormat(
            info.getTimestamp(),
            Locale.ENGLISH).format(new Date()
        );

        String fileName = info.getFileName() + timeStamp;

        try {
            File.createTempFile(fileName, info.getFileFormat(), mediaStorageDir);
            Intent intent = info.createIntent();
            intent.setAction(info.getAction());

            startActivityForResult(intent, info.getRequestCode());
        } catch (IOException e) {

            Toast
                .makeText(
                    MainActivity.this,
                    "Error creating file: " + mediaStorageDir.getAbsolutePath() + fileName + info.getFileFormat(),
                    Toast.LENGTH_SHORT)
                .show();
        }
    }
}

