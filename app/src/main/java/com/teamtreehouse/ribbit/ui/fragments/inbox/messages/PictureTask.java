package com.teamtreehouse.ribbit.ui.fragments.inbox.messages;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import com.teamtreehouse.ribbit.utils.FileHelper;

/**
 * Created by javie on 4/16/2017.
 */

public class PictureTask extends AsyncTask<Uri, Void, Uri> {

    private PictureListener listener;

    public PictureTask(PictureListener listener) {

        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.listener.onPreExecute();
    }

    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);

        this.listener.onPostExecute(uri);
    }

    @Override
    protected Uri doInBackground(Uri... uris) {

        return FileHelper.resizeUri((Context) this.listener, uris[0]);
    }
}