package com.teamtreehouse.ribbit.ui.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.SectionsPagerAdapter;
import com.teamtreehouse.ribbit.animations.TextViewAlphaIn;
import com.teamtreehouse.ribbit.animations.TextViewAlphaOut;
import com.teamtreehouse.ribbit.animations.ViewAnimationCallback;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.messages.Message;
import com.teamtreehouse.ribbit.ui.activities.intents.MultimediaResultIntent;
import com.teamtreehouse.ribbit.models.messages.MultimediaMessage;
import com.teamtreehouse.ribbit.ui.activities.intents.PictureCaptureResultIntent;
import com.teamtreehouse.ribbit.ui.activities.intents.PicturePickResultIntent;
import com.teamtreehouse.ribbit.ui.activities.intents.VideoCaptureResultIntent;
import com.teamtreehouse.ribbit.ui.activities.intents.VideoPickResultIntent;
import com.teamtreehouse.ribbit.animations.FabDown;
import com.teamtreehouse.ribbit.animations.FabUp;
import com.teamtreehouse.ribbit.ui.activities.messages.MessageActivity;
import com.teamtreehouse.ribbit.ui.activities.messages.TextMessageActivity;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.fragments.friends.FragmentFriends;
import com.teamtreehouse.ribbit.ui.fragments.inbox.FragmentInbox;
import com.teamtreehouse.ribbit.utils.Animations;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    public static final int ITEM_SELECT_CODE = 1000;
    private static int[] fabIcons = new int[]{
            R.mipmap.ic_message, R.mipmap.ic_add_contact
    };

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int TAKE_VIDEO_REQUEST = 1;
    public static final int PICK_PHOTO_REQUEST = 2;
    public static final int PICK_VIDEO_REQUEST = 3;

    private HashMap<Integer, MultimediaResultIntent> intentTypes;

    SectionsPagerAdapter viewPagerAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.intentTypes = new HashMap<>();
        this.intentTypes.put(PICK_PHOTO_REQUEST, new PicturePickResultIntent());
        this.intentTypes.put(PICK_VIDEO_REQUEST, new VideoPickResultIntent());
        this.intentTypes.put(TAKE_PHOTO_REQUEST, new PictureCaptureResultIntent());
        this.intentTypes.put(TAKE_VIDEO_REQUEST, new VideoCaptureResultIntent());

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });

        fab.setImageResource(fabIcons[0]);
        this.currentFragment = this.viewPagerAdapter.getItem(0);
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
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter("message_send");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageBroadcast, filter);
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {

        this.currentFragment.execute();

        overridePendingTransition(R.anim.slide_top_to_bottom, R.anim.slide_bottom_to_top);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            MultimediaResultIntent intentType = this.intentTypes.get(requestCode);

            Intent intent = new Intent(MainActivity.this, intentType.getActivity());
            intent.putExtra(MultimediaMessage.KEY, intentType.getUri(data, this));
            startActivity(intent);
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        // change this intent so that the usernameText can't hit "back" and getValue into the inbox

        // Set these flags to finish any other tasks that are related to Main Activity
        // This way when login fragment is launched, there is no previous fragment to go back to,
        // and this exiting the app by pressing the back button.
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
                navigateToLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isFinishing()) {

            FirebaseAuth
                    .getInstance()
                    .signOut();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case REQUEST_EXTERNAL_STORAGE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                }

                break;
        }
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
        choosePhotoIntent.setType("image/*");
        startActivityForResult(choosePhotoIntent, MainActivity.PICK_PHOTO_REQUEST);
    }

    @OnClick(R.id.fabCapturePictureMessage)
    public void onCapturePictureMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);
        if (isExternalStorageAvailable()) {
            // getValue the URI

            // 1. Get the external storage directory
            File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            // 2. Create a unique file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "IMG_" + timeStamp;

            try {
                File.createTempFile(fileName, ".jpg", mediaStorageDir);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
            } catch (IOException e) {

                Toast.makeText(this, "Error creating file: " +
                        mediaStorageDir.getAbsolutePath() + fileName + ".jpg", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.fabVideoMessage)
    public void onVideoMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);
        Intent chooseVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseVideoIntent.setType("video/*");
        Toast.makeText(MainActivity.this, R.string.video_file_size_warning, Toast.LENGTH_LONG).show();
        startActivityForResult(chooseVideoIntent, PICK_VIDEO_REQUEST);
    }

    @OnClick(R.id.fabCaptureVideoMessage)
    public void onVideoCaptureMessageClick(View view) {

        this.hideFabMenu();
        this.setFabIcon(R.mipmap.ic_message);
        if (isExternalStorageAvailable()) {
            // getValue the URI

            // 1. Get the external storage directory
            File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);

            // 2. Create a unique file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "VID_" + timeStamp;

            try {
                File.createTempFile(fileName, ".mp4", mediaStorageDir);

                // 4. Return the file's URI
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
                videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // 0 = lowest res
                startActivityForResult(videoIntent, TAKE_VIDEO_REQUEST);
            } catch (IOException e) {

                Toast.makeText(this, "Error creating file: " +
                        mediaStorageDir.getAbsolutePath() + fileName + ".mp4", Toast.LENGTH_SHORT).show();
            }
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
}

