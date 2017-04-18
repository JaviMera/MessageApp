package com.teamtreehouse.ribbit.ui.fragments.inbox.messages;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.ImageMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/14/2017.
 */

public class FragmentImageMessage
    extends
        FragmentMessage<Uri>
    implements
        PictureListener {

    public static Fragment newInstance(Uri uri) {

        FragmentImageMessage fragmentImageMessage = new FragmentImageMessage();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ImageMessage.KEY, uri);
        fragmentImageMessage.setArguments(bundle);

        return fragmentImageMessage;
    }

    @BindView(R.id.imageView)
    ImageView pictureView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_message, container, false);

        ButterKnife.bind(this, view);

        Toast.makeText(getActivity(), "Converting picture...", Toast.LENGTH_SHORT).show();

        this.value = getArguments().getParcelable(ImageMessage.KEY);
        new PictureTask(this).execute(this.value);
        return view;
    }

    @Override
    public void onPreExecute() {

        parent.setSendLayoutVisibility(View.GONE);
        parent.setProgressBarVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(Uri resizedPictureUri) {

        parent.setSendLayoutVisibility(View.VISIBLE);
        parent.setProgressBarVisibility(View.GONE);

        this.value = resizedPictureUri;
        Picasso.with(getActivity()).load(value).into(pictureView);
    }
}
