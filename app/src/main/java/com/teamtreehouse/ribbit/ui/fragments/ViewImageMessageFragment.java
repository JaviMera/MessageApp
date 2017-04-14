package com.teamtreehouse.ribbit.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.ui.activities.ViewMessageActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewImageMessageFragment extends Fragment implements ViewMessageFragment {

    @BindView(R.id.imageView)
    ImageView image;

    private ImageMessage message;

    private ViewMessageActivity parent;

    public static ViewImageMessageFragment newInstance(ImageMessage message, Bundle bundle) {

        ViewImageMessageFragment fragment = new ViewImageMessageFragment();
        bundle.putParcelable("message", message);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = (ViewMessageActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_image_fragment, container, false);

        ButterKnife.bind(this, view);
        message = getArguments().getParcelable("message");

        ((ViewMessageActivity)getActivity()).showProgress();

        FirebaseStorage
            .getInstance()
            .getReferenceFromUrl(message.getUrl())
            .getBytes(1024 * 1024 * 10)
            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(bitmap);
                    ((ViewMessageActivity)getActivity()).hideProgress();
                    parent.start();
                }
            })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                parent.exit();
            }
        });

        return view;
    }

    @Override
    public void onFinish() {

        // Delete the image from storage reference
        FirebaseStorage
            .getInstance()
            .getReference()
            .child(message.getPath())
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });

        // Delete image info from database reference
        FirebaseDatabase
            .getInstance()
            .getReference()
            .child("messages")
            .child("images")
            .child(Auth.getInstance().getId())
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        HashMap<String, String> currentMessage = (HashMap<String, String>) ds.getValue();
                        if (currentMessage.get("path").equals(message.getPath())) {

                            ds.getRef().setValue(null);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
