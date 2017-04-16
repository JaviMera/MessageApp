package com.teamtreehouse.ribbit.ui.fragments.inbox.messages;

import android.net.Uri;

/**
 * Created by javie on 4/16/2017.
 */

public interface PictureListener {

    void onPreExecute();
    void onPostExecute(Uri newUri);
}
