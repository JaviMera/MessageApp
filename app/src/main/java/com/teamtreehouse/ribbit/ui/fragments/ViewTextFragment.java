package com.teamtreehouse.ribbit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.Item;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.TextMessage;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 4/13/2017.
 */

public class ViewTextFragment extends Fragment implements ViewMessageFragment{

    private TextMessage friendMessage;

    @BindView(R.id.messageTextView)
    TextView messageTextView;

    public static ViewTextFragment newInstance(TextMessage message) {

        ViewTextFragment fragment = new ViewTextFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("message", message);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_text_fragment, container, false);

        ButterKnife.bind(this, view);

        friendMessage = getArguments().getParcelable("message");
        messageTextView.setText(friendMessage.getText());

        return view;
    }

    @Override
    public void onFinish() {

        FirebaseDatabase
            .getInstance()
            .getReference()
            .child("messages")
            .child("text")
            .child(Auth.getInstance().getId())
            .runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {

                    for(MutableData md : mutableData.getChildren()) {

                        HashMap<String, String> map = (HashMap<String, String>) md.getValue();

                        if(map.get("id").equals(friendMessage.getId())) {

                            md.setValue(null);
                            return Transaction.success(mutableData);
                        }
                    }

                    return null;
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                    if(databaseError == null) {

                    }

                }
            }
        );
    }
}
