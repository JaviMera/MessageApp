package com.teamtreehouse.ribbit.ui.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.ui.activities.MessageActivity;
import com.teamtreehouse.ribbit.utils.FileHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/14/2017.
 */

public class FragmentImageMessage extends Fragment {

    public static Fragment newInstance(Uri uri) {

        FragmentImageMessage fragmentImageMessage = new FragmentImageMessage();
        Bundle bundle = new Bundle();
        bundle.putParcelable("uri", uri);
        fragmentImageMessage.setArguments(bundle);

        return fragmentImageMessage;
    }

    @BindView(R.id.imageView)
    ImageView pictureView;

    private Uri pictureUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_message, container, false);

        ButterKnife.bind(this, view);

        pictureUri = getArguments().getParcelable("uri");
        new PictureTask().execute(pictureUri);
        return view;
    }

    public class PictureTask extends AsyncTask<Uri, Void, Uri> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ((MessageActivity)getActivity()).showProgress();
        }

        @Override
        protected void onPostExecute(Uri uri) {
            super.onPostExecute(uri);

            ((MessageActivity)getActivity()).hideProgress();
            pictureUri = uri;
            Picasso.with(getActivity()).load(pictureUri).into(pictureView);
        }

        @Override
        protected Uri doInBackground(Uri... uris) {

            return FileHelper.resizeUri(getActivity(), uris[0]);
        }
    }

    public Uri getUri() {

        return this.pictureUri;
    }
}
