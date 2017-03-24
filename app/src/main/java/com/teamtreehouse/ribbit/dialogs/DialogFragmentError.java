package com.teamtreehouse.ribbit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.ui.LoginActivity;

/**
 * Created by javie on 3/24/2017.
 */

public class DialogFragmentError extends DialogFragment {

    public static DialogFragmentError newInstance(String message) {

        DialogFragmentError dialogFragment = new DialogFragmentError();
        Bundle bundle = new Bundle();
        bundle.putString("dialog_message", message);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the message from bundle
        String message = getArguments().getString("dialog_message");

        return new AlertDialog
            .Builder(getContext())
            .setMessage(message)
            .setTitle(R.string.login_error_title)
            .setPositiveButton(android.R.string.ok, null)
            .create();
    }
}
