package com.teamtreehouse.ribbit.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.ui.activities.TextMessageActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by javie on 4/9/2017.
 */

public class MessageOptionDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.dialog_message_options, null);

        ButterKnife.bind(this, view);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setView(view);

        return dialog.create();
    }

    @OnClick({
        R.id.textLayout,
        R.id.picturesLayout,
        R.id.capturePicturesLayout,
        R.id.videoLayout,
        R.id.captureVideoLayout})
    public void onOptionClick(View view){

        switch(view.getId()) {

            case R.id.textLayout:

                Intent intent = new Intent(getActivity(), TextMessageActivity.class);
                startActivity(intent);
                break;
        }

        dismiss();
    }
}