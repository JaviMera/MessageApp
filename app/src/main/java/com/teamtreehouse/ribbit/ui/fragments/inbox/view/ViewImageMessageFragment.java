package com.teamtreehouse.ribbit.ui.fragments.inbox.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.DeletePictureCallback;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.database.MessageStorage;
import com.teamtreehouse.ribbit.models.ImageMessage;
import com.teamtreehouse.ribbit.models.Message;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewImageMessageFragment extends ViewFragmentMessage implements ViewMessageFragment {

    public static final int MAX_IMAGE_MEMORY = 1024 * 1024 * 10;

    @BindView(R.id.imageView)
    ImageView image;

    private ImageMessage message;

    public static ViewImageMessageFragment newInstance(ImageMessage message, Bundle bundle) {

        ViewImageMessageFragment fragment = new ViewImageMessageFragment();
        bundle.putParcelable(Message.KEY, message);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_image, container, false);

        ButterKnife.bind(this, view);
        message = getArguments().getParcelable(Message.KEY);

        parent.setProgressbarVisibility(View.VISIBLE);
        parent.setCardViewVisibility(View.GONE);

        FirebaseStorage
            .getInstance()
            .getReferenceFromUrl(message.getUrl())
            .getBytes(MAX_IMAGE_MEMORY)
            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(bitmap);
                    parent.setProgressbarVisibility(View.GONE);
                    parent.setCardViewVisibility(View.VISIBLE);
                    parent.start();
                }
            })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                parent.finish();
            }
        });

        return view;
    }

    @Override
    public void onFinish() {

        // Delete the image from storage reference
        MessageStorage.deletePicture(message.getPath(), new DeletePictureCallback() {
            @Override
            public void onSuccess() {

                MessageDB.deleteImageMessage(message, new DeletePictureCallback() {

                    @Override
                    public void onSuccess() {

                        Toast.makeText(parent, "Message Viewed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message) {

                        Toast.makeText(parent, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
