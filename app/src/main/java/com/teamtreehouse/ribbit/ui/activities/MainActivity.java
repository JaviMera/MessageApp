package com.teamtreehouse.ribbit.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.SectionsPagerAdapter;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.purgatory.ObsoleteUser;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;
import com.teamtreehouse.ribbit.ui.fragments.friends.FriendsFragment;
import com.teamtreehouse.ribbit.ui.fragments.messages.FragmentMessages;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ActivityView {

    private static int[] fabIcons = new int[]{
        R.mipmap.ic_message, R.mipmap.ic_add_contact
    };

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int TAKE_VIDEO_REQUEST = 1;
    public static final int PICK_PHOTO_REQUEST = 2;
    public static final int PICK_VIDEO_REQUEST = 3;

    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    public static final int FILE_SIZE_LIMIT = 1024 * 1024 * 10; // 10 MB

    protected Uri mMediaUri;

    protected DialogInterface.OnClickListener mDialogListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Take picture
                            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            if (mMediaUri == null) {
                                // display an error
                                Toast.makeText(MainActivity.this, R.string.error_external_storage,
                                        Toast.LENGTH_LONG).show();
                            } else {
                                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                                startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                            }
                            break;
                        case 1: // Take video
                            Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                            if (mMediaUri == null) {
                                // display an error
                                Toast.makeText(MainActivity.this, R.string.error_external_storage,
                                        Toast.LENGTH_LONG).show();
                            } else {
                                videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                                videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                                videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // 0 = lowest res
                                startActivityForResult(videoIntent, TAKE_VIDEO_REQUEST);
                            }
                            break;
                        case 2: // Choose picture
                            Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            choosePhotoIntent.setType("image/*");
                            startActivityForResult(choosePhotoIntent, PICK_PHOTO_REQUEST);
                            break;
                        case 3: // Choose video
                            Intent chooseVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            chooseVideoIntent.setType("video/*");
                            Toast.makeText(MainActivity.this, R.string.video_file_size_warning, Toast.LENGTH_LONG).show();
                            startActivityForResult(chooseVideoIntent, PICK_VIDEO_REQUEST);
                            break;
                    }
                }

                private Uri getOutputMediaFileUri(int mediaType) {
                    // To be safe, you should check that the SDCard is mounted
                    // using Environment.getExternalStorageState() before doing this.
                    if (isExternalStorageAvailable()) {
                        // get the URI

                        // 1. Get the external storage directory
                        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                        // 2. Create a unique file name
                        String fileName = "";
                        String fileType = "";
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                        if (mediaType == MEDIA_TYPE_IMAGE) {
                            fileName = "IMG_" + timeStamp;
                            fileType = ".jpg";
                        } else if (mediaType == MEDIA_TYPE_VIDEO) {
                            fileName = "VID_" + timeStamp;
                            fileType = ".mp4";
                        } else {
                            return null;
                        }

                        // 3. Create the file
                        File mediaFile;
                        try {
                            mediaFile = File.createTempFile(fileName, fileType, mediaStorageDir);
                            Log.i(TAG, "File: " + Uri.fromFile(mediaFile));

                            // 4. Return the file's URI
                            return Uri.fromFile(mediaFile);
                        } catch (IOException e) {
                            Log.e(TAG, "Error creating file: " +
                                    mediaStorageDir.getAbsolutePath() + fileName + fileType);
                        }
                    }

                    // something went wrong
                    return null;
                }

                private boolean isExternalStorageAvailable() {
                    String state = Environment.getExternalStorageState();

                    if (!state.equals(Environment.MEDIA_MOUNTED)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter viewPagerAdapter;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private FragmentPager currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Auth.getInstance().getUsername());

        viewPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager(), new FragmentMessages(), new FriendsFragment());

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(viewPagerAdapter);

        // Tell the tab layout to set the view pager object
        tabLayout.setupWithViewPager(viewPager);

        // Setup icons to display for each tab
        for(int tab = 0; tab < tabLayout.getTabCount(); tab++) {

            TabLayout.Tab currentTab = tabLayout.getTabAt(tab);

            if(currentTab != null) {

                currentTab.setIcon(viewPagerAdapter.getIcon(tab));
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

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

    @OnClick(R.id.fab)
    public void onFabClick(View view) {

        this.currentFragment.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == PICK_PHOTO_REQUEST && data != null) {

            Uri uri = data.getData();
            Intent intent = new Intent(MainActivity.this, ImageMessageActivity.class);
            intent.putExtra("picture", uri);
            startActivity(intent);
        }

//        else if (resultCode == RESULT_OK) {
//
//            if (requestCode == PICK_PHOTO_REQUEST || requestCode == PICK_VIDEO_REQUEST) {
//                if (data == null) {
//                    Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
//                } else {
//                    mMediaUri = data.getData();
//                }
//
//                Log.i(TAG, "Media URI: " + mMediaUri);
//                if (requestCode == PICK_VIDEO_REQUEST) {
//                    // make sure the file is less than 10 MB
//                    int fileSize = 0;
//                    InputStream inputStream = null;
//
//                    try {
//                        inputStream = getContentResolver().openInputStream(mMediaUri);
//                        fileSize = inputStream.available();
//                    } catch (FileNotFoundException e) {
//                        Toast.makeText(this, R.string.error_opening_file, Toast.LENGTH_LONG).show();
//                        return;
//                    } catch (IOException e) {
//                        Toast.makeText(this, R.string.error_opening_file, Toast.LENGTH_LONG).show();
//                        return;
//                    } finally {
//                        try {
//                            inputStream.close();
//                        } catch (IOException e) { /* Intentionally blank */ }
//                    }
//
//                    if (fileSize >= FILE_SIZE_LIMIT) {
//                        Toast.makeText(this, R.string.error_file_size_too_large, Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                }
//            } else {
//                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                mediaScanIntent.setData(mMediaUri);
//                sendBroadcast(mediaScanIntent);
//            }
//
//            Intent recipientsIntent = new Intent(this, RecipientsActivity.class);
//            recipientsIntent.setData(mMediaUri);
//
//            String fileType;
//            if (requestCode == PICK_PHOTO_REQUEST || requestCode == TAKE_PHOTO_REQUEST) {
//                fileType = TextMessage.TYPE_IMAGE;
//            } else {
//                fileType = TextMessage.TYPE_VIDEO;
//            }
//
//            recipientsIntent.putExtra(TextMessage.KEY_FILE_TYPE, fileType);
//            startActivity(recipientsIntent);
//        } else if (resultCode != RESULT_CANCELED) {
//            Toast.makeText(this, R.string.general_error, Toast.LENGTH_LONG).show();
//        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        // change this intent so that the usernameText can't hit "back" and get into the inbox

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
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_logout:
                ObsoleteUser.logOut();
                navigateToLogin();
                break;
            case R.id.action_edit_friends:
                Intent intent = new Intent(this, EditFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_camera:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(R.array.camera_choices, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(isFinishing()) {

            FirebaseAuth
                .getInstance()
                .signOut();
        }
    }

    @Override
    public void itemSelect(Intent intent) {

        startActivityForResult(intent, 1000);
    }

    public void requestPermissions() {

        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode) {

            case REQUEST_EXTERNAL_STORAGE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    FragmentMessages fragmentMessages = (FragmentMessages) this.viewPagerAdapter.getItem(0);
                    fragmentMessages.viewImageActivity();
                }

                break;
        }
    }
}
