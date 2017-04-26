package com.teamtreehouse.ribbit.utils;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.StorageReference;
import com.teamtreehouse.ribbit.FirebaseImageLoader;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.ui.fragments.FragmentRecycler;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by javie on 4/24/2017.
 */

public class GlideUtils {

    public static void loadDefault(Context context, CircleImageView view){

        Glide
            .with(context)
            .load(R.mipmap.ic_person)
            .into(view);
    }

    public static void loadDefault(Context context, ImageView view){

        Glide
            .with(context)
            .load(R.mipmap.ic_person)
            .into(view);
    }

    public static void loadFromServer(Context context, StorageReference ref, CircleImageView view) {

        Glide
            .with(context)
            .using(new FirebaseImageLoader())
            .load(ref)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .fitCenter()
            .into(view);
    }

    public static void loadFromServer(Context context, StorageReference ref, ImageView view) {

        Glide
            .with(context)
            .using(new FirebaseImageLoader())
            .load(ref)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(view);
    }
}
