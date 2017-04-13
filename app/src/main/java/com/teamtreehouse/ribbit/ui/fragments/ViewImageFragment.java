package com.teamtreehouse.ribbit.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/12/2017.
 */

public class ViewImageFragment extends Fragment {

    @BindView(R.id.imageView)
    ImageView pictureView;

    public static ViewImageFragment newInstance(Uri photoUri) {

        ViewImageFragment fragment = new ViewImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("photo", photoUri);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_image_fragment, container, false);

        ButterKnife.bind(this, view);

        Uri uri = getArguments().getParcelable("photo");
        Picasso.with(this.getContext()).load(uri).into(this.pictureView);
        return view;
    }
}
